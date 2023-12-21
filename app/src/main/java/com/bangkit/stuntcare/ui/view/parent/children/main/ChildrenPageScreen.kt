package com.bangkit.stuntcare.ui.view.parent.children.main

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.ChildrenBoxInfo
import com.bangkit.stuntcare.ui.component.FoodCard
import com.bangkit.stuntcare.ui.component.NutritionIndicator
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.theme.Blue100
import com.bangkit.stuntcare.ui.theme.Blue300
import com.bangkit.stuntcare.ui.theme.Blue500
import com.bangkit.stuntcare.ui.theme.Blue700
import com.bangkit.stuntcare.ui.theme.Blue900
import com.bangkit.stuntcare.ui.theme.Green600
import com.bangkit.stuntcare.ui.theme.Green700
import com.bangkit.stuntcare.ui.theme.Grey100
import com.bangkit.stuntcare.ui.theme.Yellow600
import com.bangkit.stuntcare.ui.utils.convertDateAndLongToAge
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.utils.removeTrailingZeros
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

@Composable
fun ChildrenScreen(
    viewModel: ChildrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    childrenId: String? = null,
    navigator: ChildrenScreenNavigator
) {


    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = context){
        viewModel.getAllChildren()
    }

    viewModel.allChild.collectAsState(UiState.Loading).value.let { childItem ->
        when (childItem) {
            is UiState.Loading -> {
                scope.launch {
                }
            }

            is UiState.Success -> {
                val listChild = childItem.data

                LaunchedEffect(key1 = childrenId ?: listChild.first().id){
                    viewModel.getChildrenById(childrenId ?: listChild.first().id)
                }
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
                    when (it) {
                        is UiState.Loading -> {
                        }

                        is UiState.Success -> {
                            val child = it.data
                            val gender = if (child.data.gender == "Laki-laki") "Boy" else "Girl"

                            viewModel.statusChildren.collectAsState().value.let {
                                when (it) {
                                    is UiState.Loading -> {
                                        scope.launch {
                                            viewModel.getStatusChildren(
                                                dateToDay(child.data.birthDay),
                                                gender,
                                                23f,
                                                60f
                                            )
                                        }
                                    }

                                    is UiState.Success -> {
                                        val statusChildren = it.data
                                        ChildrenTestScreen(
                                            navigator = navigator,
                                            allChildren = listChild,
                                            children = child,
                                            statusChildren = statusChildren,
                                            viewModel = viewModel
                                        )
                                    }

                                    is UiState.Error -> {

                                    }
                                }
                            }
                        }

                        is UiState.Error -> {
                            showToast(it.errorMessage, context)
                        }
                    }
                }
            }

            is UiState.Error -> {
                showToast(childItem.errorMessage, context)
            }
        }
    }
}

@Composable
fun NoDisplay(navigator: ChildrenScreenNavigator, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (tvDataEmpty, btnAddChildren) = createRefs()
        Text(
            text = "Tidak Ada Data Anak, Silahkan Tambahkan Terlebih Dahulu",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            maxLines = 2,
            modifier = modifier.constrainAs(tvDataEmpty) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom)
            }
        )
        Button(
            onClick = { navigator.navigateToAddChildren() },
            shape = RoundedCornerShape(0.dp),
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(btnAddChildren) {
                    top.linkTo(tvDataEmpty.bottom, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    start.linkTo(parent.start, margin = 12.dp)
                }
        ) {
            Text(text = "Tambahkan Data Anak")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildrenTestScreen(
    children: DetailChildrenResponse,
    allChildren: List<ChildItem>,
    statusChildren: ChildrenStatusResponse,
    navigator: ChildrenScreenNavigator,
    viewModel: ChildrenViewModel,
    modifier: Modifier = Modifier,
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

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = {
                Text(
                    text = statusChildren.stunting.message,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            actions = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }

                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Riwayat Perkembangan Anak") },
                        onClick = { navigator.navigateToGrowthHistoryChildren(children.data.id) }
                    )

                    DropdownMenuItem(
                        text = { Text(text = "Edit Profil Anak") },
                        onClick = { navigator.navigateToEditProfileChildren(children.data.id) }
                    )
                }
            }
        )

        if (showListChildren) {
            ModalBottomSheet(onDismissRequest = { showListChildren = !showListChildren }) {
                LazyColumn {
                    items(allChildren, { it.id }) { child ->
                        val gender = if (child.gender == "Laki-laki") "Boy" else "Girl"

                        viewModel.statusChildren.collectAsState().value.let {
                            when (it) {
                                is UiState.Loading -> {
                                    scope.launch {
                                        viewModel.getStatusChildren(
                                            dateToDay(child.birthDay),
                                            gender,
                                            23f,
                                            60f
                                        )
                                    }
                                }

                                is UiState.Success -> {
                                    val statusChildren = it.data
                                    CardChild(
                                        children = child,
                                        status = statusChildren.stunting.message,
                                        modifier = modifier.clickable {
                                            viewModel.getChildrenById(child.id)
                                            showListChildren = false
                                        }
                                    )
                                }

                                is UiState.Error -> {

                                }
                            }
                        }
                    }
                }
            }
        }

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
                    text = children.data.name,
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

        // Tab Row
        Row(
            modifier = modifier.padding(top = 8.dp)
        ) {
            Text(text = "Statistik Perkmebangan",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = if (statisticRowSelected) {
                    Blue900
                } else {
                    Blue500
                },
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 16.dp))
                    .background(if (statisticRowSelected) Blue100 else Color.White)
                    .clickable {
                        statisticRowSelected = !statisticRowSelected
                    }
                    .weight(1f)
                    .padding(horizontal = 26.dp, vertical = 8.dp))
            Text(
                text = "Statistik Perkmebangan",
                fontWeight = FontWeight.SemiBold,
                color = if (!statisticRowSelected) {
                    Blue900
                } else {
                    Blue500
                },
                fontSize = 12.sp,
                modifier = modifier
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .background(if (!statisticRowSelected) Blue100 else Color.White)
                    .clickable {
                        statisticRowSelected = !statisticRowSelected
                    }
                    .weight(1f)
                    .padding(horizontal = 26.dp, vertical = 8.dp)
            )
        }
        if (statisticRowSelected) {
            GrowthStatistic(
                children = children,
                statusChildren = statusChildren,
                navigator = navigator
            )
        } else {
            DailyChildren(navigator = navigator)
        }
    }
}

@Composable
fun GrowthStatistic(
    children: DetailChildrenResponse,
    navigator: ChildrenScreenNavigator,
    statusChildren: ChildrenStatusResponse,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(Blue100)
            .padding(24.dp)
            .fillMaxSize()
    ) {

        ChildrenDataSection(children = children, navigator = navigator)

        // Line Chart TODO
        ChildrenStatisticSection(children = children)

        ChildrenDiagnosisSection(statusChildren = statusChildren, navigator = navigator)
    }
}

@Composable
fun ChildrenDataSection(
    children: DetailChildrenResponse,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Update Terakhir: ${Date(children.data.growthHistory.first().createdAt)}",
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ChildrenBoxInfo(
                title = "Tinggi Badan",
                data = removeTrailingZeros(children.data.growthHistory.first().height),
                unit = "cm"
            )
            ChildrenBoxInfo(
                title = "Berat Badan",
                data = removeTrailingZeros(children.data.growthHistory.first().weight),
                unit = "kg"
            )
            ChildrenBoxInfo(
                title = "Usia Anak",
                data = convertDateAndLongToAge(
                    dateBegin = children.data.birthDay,
                    dateEnd = children.data.growthHistory.first().createdAt
                ).toString(),
                unit = "hari"
            )
        }
        Button(
            onClick = { navigator.navigateToUpdateData(children.data.id) },
            modifier = modifier.padding(8.dp)
        ) {
            Text(text = "Update Data Anak")
        }
    }
}

@Composable
fun ChildrenStatisticSection(
    children: DetailChildrenResponse,
    modifier: Modifier = Modifier
) {
    val growthValue: Array<Float> = children.data.growthHistory.reversed().map {
        it.weight
    }.toTypedArray()

    val color: List<Color> = listOf(Color.Green, Color.Blue)

    val mLineComponent: MutableList<LineComponent> = mutableListOf()

    val mLineChart: MutableList<LineChart.LineSpec> = mutableListOf()

    color.map {
        val lineComponentData = LineComponent(
            color = it.toArgb(),
            thicknessDp = 2f
        )
        val lineChartData = LineChart.LineSpec(
            lineColor = it.toArgb(),
        )
        mLineComponent.add(lineComponentData)
        mLineChart.add(lineChartData)
    }
    val chartEntryModel = entryModelOf(entriesOf(*growthValue))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.Bottom, modifier = modifier
                .background(Grey100)
                .padding(12.dp)
        ) {
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Green700)
            ) {
                Text(text = "Tinggi Badan")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Yellow600)
            ) {
                Text(text = "Berat Badan")
            }
        }
    }
}

@Composable
fun ChildrenDiagnosisSection(
    statusChildren: ChildrenStatusResponse,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier,
) {
    var stuntingStatus by remember {
        mutableStateOf(false)
    }

    var underweightStatus by remember {
        mutableStateOf(false)
    }

    var wastingStatus by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(28.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(9.dp)
        ) {
            Text(
                text = "Diagnosa Anak",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(bottom = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier.weight(0.1f)
                )
                Text(
                    text = "Status Stunting",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.1f)
                        .align(Alignment.CenterVertically)
                )
                Card(
                    modifier = modifier
                        .wrapContentSize()
                        .clickable {
                            stuntingStatus = !stuntingStatus
                        }
                        .weight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = Green600)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.stunting.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 10f,
                                    offset = Offset(0.5f, 2f)
                                )
                            ),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier.size(16.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = stuntingStatus) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.tvStuntingDescription,
                                statusChildren.stunting.description
                            ),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                        Spacer(modifier = modifier.size(4.dp))
                        Text(
                            text = stringResource(
                                R.string.tvStuntingAction,
                                statusChildren.stunting.recommendation
                            ), fontSize = 8.sp, fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier.weight(0.1f)
                )
                Text(
                    text = "Status Underweight",
                    fontSize = 12.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.weight(0.1f)
                )
                Card(
                    modifier = modifier
                        .wrapContentSize()
                        .clickable {
                            underweightStatus = !underweightStatus
                        }
                        .weight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = Green600)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.underweight.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 10f,
                                    offset = Offset(0.5f, 2f)
                                )
                            ),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier.size(16.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = underweightStatus) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.tvStuntingDescription,
                                statusChildren.underweight.description
                            ),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                        Spacer(modifier = modifier.size(4.dp))
                        Text(
                            text = stringResource(
                                R.string.tvStuntingAction,
                                statusChildren.underweight.recommendation
                            ),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier.weight(0.1f)
                )
                Text(
                    text = "Status Wasting",
                    fontSize = 12.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.weight(0.1f)
                )
                Card(
                    modifier = modifier
                        .wrapContentSize()
                        .clickable {
                            wastingStatus = !wastingStatus
                        }
                        .weight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = Green600)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.wasted.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    blurRadius = 10f,
                                    offset = Offset(0.5f, 2f)
                                )
                            ),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = modifier.size(16.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = wastingStatus) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.tvStuntingDescription,
                                statusChildren.wasted.description
                            ),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Normal,
                        )
                        Spacer(modifier = modifier.size(4.dp))
                        Text(
                            text = stringResource(
                                R.string.tvStuntingAction,
                                statusChildren.wasted.recommendation
                            ), fontSize = 8.sp, fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyChildren(
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Blue100)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Update Terakhir: 12/12/1221",
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold,
            color = Blue900,
            modifier = modifier.padding(bottom = 12.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Capaian Harian Gizi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue700
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${90}%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green600
                    ) // TODO
                    Text(
                        text = "Terpenuhi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue700
                    )
                }

                NutritionIndicator(title = "Karbohidrat", currentValue = 124f, maximumValue = 231f)
                NutritionIndicator(title = "Karbohidrat", currentValue = 124f, maximumValue = 231f)
                NutritionIndicator(title = "Karbohidrat", currentValue = 124f, maximumValue = 231f)
                NutritionIndicator(title = "Karbohidrat", currentValue = 124f, maximumValue = 231f)
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier.padding(top = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Catatan Menu Harian",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue700
                )
                Text(
                    text = "Catat Menu Dengan Fitur Kalisifikasi Gambar",
                    fontSize = 10.sp,
                    color = Blue900,
                    fontWeight = FontWeight.Light,
                    modifier = modifier.padding(8.dp)
                )
                FoodCard(
                    title = "Sarapan Pagi",
                    foodName = "Seblak",
                    image = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D",
                    navigateToCamera = {navigator.navigateToFoodClassification()})

                Spacer(modifier = Modifier.height(8.dp))

                FoodCard(
                    title = "Makan Siang",
                    foodName = "Seblak",
                    image = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D",
                    navigateToCamera = {})

                Spacer(modifier = Modifier.height(8.dp))

                FoodCard(
                    title = "Makan Malam",
                    foodName = "Seblak",
                    image = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D",
                    navigateToCamera = {})
            }
        }
    }
}