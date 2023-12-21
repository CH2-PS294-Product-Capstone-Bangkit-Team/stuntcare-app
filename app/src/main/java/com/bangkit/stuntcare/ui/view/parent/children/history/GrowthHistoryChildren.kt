package com.bangkit.stuntcare.ui.view.parent.children.history

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bangkit.stuntcare.data.remote.response.DataChildrenResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.ChildrenBoxInfo
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.theme.Blue300
import com.bangkit.stuntcare.ui.theme.Blue900
import com.bangkit.stuntcare.ui.theme.Grey100
import com.bangkit.stuntcare.ui.utils.convertDateAndLongToAge
import com.bangkit.stuntcare.ui.utils.convertLongToDateString
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.bangkit.stuntcare.ui.view.parent.children.main.ChildrenTestScreen
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

@Composable
fun GrowthHistoryScreen(
    childrenId: String?,
    navigator: ChildrenScreenNavigator,
    viewModel: GrowthHistoryChildrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllChildren()
            }

            is UiState.Success -> {
                val data = it.data.data
                val children = childrenId ?: data.child.first().id

                viewModel.childrenById.collectAsState(initial = UiState.Loading).value.let {
                    when (it) {
                        is UiState.Loading -> {
                            if (childrenId != null) {
                                viewModel.getChildrenById(childrenId)
                            }
                        }

                        is UiState.Success -> {
                            val child = it.data
                            val gender = if (child.data.gender == "Laki-laki") "Boy" else "Girl"


                            val statusChildren = runBlocking {
                                viewModel.getStatusChildren(
                                    dateToDay(child.data.birthDay),
                                    gender,
                                    23f,
                                    60f
                                )
                            }
                            GrowthHistoryContent(data, navigator, child, viewModel)

                        }

                        is UiState.Error -> {
                            showToast(it.errorMessage, context)
                        }
                    }
                }

            }

            is UiState.Error -> {
                val message = it.errorMessage
                showToast(message, LocalContext.current)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowthHistoryContent(
    data: DataChildrenResponse,
    navigator: ChildrenScreenNavigator,
    childrenById: DetailChildrenResponse,
    viewModel: GrowthHistoryChildrenViewModel,
    modifier: Modifier = Modifier
) {
    var showListChildren by remember {
        mutableStateOf(false)
    }
    Column {
        GrowthTopBarSection(
            data = data,
            viewModel = viewModel,
            childrenById = childrenById,
            navigator = navigator
        )
        HistorySection(data, childrenById, navigator)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowthTopBarSection(
    data: DataChildrenResponse,
    childrenById: DetailChildrenResponse,
    viewModel: GrowthHistoryChildrenViewModel,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    var showMenu by remember {
        mutableStateOf(false)
    }

    var showListChildren by remember {
        mutableStateOf(false)
    }

    var statisticRowSelected by remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Riwayat Perkembangan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        ConstraintLayout(
            modifier = modifier.fillMaxWidth()
        ) {
            val (tvSelectChildren, ddChildren) = createRefs()
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .background(Blue300)
                    .clickable {
                        showListChildren = !showListChildren
                    }
                    .constrainAs(ddChildren) {
                        top.linkTo(tvSelectChildren.top)
                        start.linkTo(tvSelectChildren.end)
                        bottom.linkTo(tvSelectChildren.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = childrenById.data.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(vertical = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding()
                )
            }

            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(tvSelectChildren) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            ) { // TODO
                Text(
                    text = "Pilih Anak",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 28.dp)
                )
            }
        }

        if (showListChildren) {
            ModalBottomSheet(onDismissRequest = { showListChildren = !showListChildren }) {
                LazyColumn {
                    items(data.child, { it.id }) { child ->
                        val gender = if (child.gender == "Laki-laki") "Boy" else "Girl"
                        var statusStunting by remember {
                            mutableStateOf("")
                        }

                        LaunchedEffect(key1 = child) {
                            val response = viewModel.getStatusChildren(
                                dateToDay(child.birthDay),
                                gender,
                                child.weight,
                                child.height
                            )
                            statusStunting = response.stunting.message
                        }

                        CardChild(children = child, status = statusStunting)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HistorySection(
    data: DataChildrenResponse,
    childrenById: DetailChildrenResponse,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    var isDialogDateShow by remember {
        mutableStateOf(false)
    }

    var birthDate: LocalDate? by remember {
        mutableStateOf(null)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Button(
                onClick = { isDialogDateShow = !isDialogDateShow },
                shape = RoundedCornerShape(12.dp),
                modifier = modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = "Pilih Tanggal")
            }
        }
        items(childrenById.data.growthHistory, { it.id }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Grey100),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = convertLongToDateString(it.createdAt),
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = Blue900
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = modifier
                    ) {
                        ChildrenBoxInfo(
                            title = "Tinggi Badan",
                            data = it.height.toString(),
                            unit = "cm"
                        )
                        ChildrenBoxInfo(
                            title = "Berat Badan",
                            data = it.weight.toString(),
                            unit = "kg"
                        )
                        ChildrenBoxInfo(
                            title = "Usia",
                            data = convertDateAndLongToAge(
                                childrenById.data.birthDay,
                                it.createdAt
                            ).toString(),
                            unit = "hari"
                        )
                    }
                }
            }
        }
    }

    if (isDialogDateShow) {
        DatePickerDialog(
            onDismissRequest = { isDialogDateShow = false },
            onDateChange = {
                birthDate = it
                isDialogDateShow = false
            },
            title = { Text(text = "Pilih Tanggal Konsultasi") }
        )
    }
}