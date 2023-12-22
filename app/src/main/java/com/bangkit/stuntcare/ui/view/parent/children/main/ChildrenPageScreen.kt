package com.bangkit.stuntcare.ui.view.parent.children.main

import android.util.Log
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.data.remote.response.ChildrenFoodResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.data.remote.response.FoodRecommendationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.ChildrenBoxInfo
import com.bangkit.stuntcare.ui.component.FoodCard
import com.bangkit.stuntcare.ui.component.FoodRecommendationCard
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
import com.bangkit.stuntcare.ui.utils.convertLongToDateString
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.utils.removeTrailingZeros
import com.bangkit.stuntcare.ui.utils.showToast
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.gson.Gson
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
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

    viewModel.allChild.collectAsState(UiState.Loading).value.let { childItem ->
        when (childItem) {
            is UiState.Loading -> {
                viewModel.getAllChildren()
            }

            is UiState.Success -> {
                val listChild = childItem.data
                if (listChild.isEmpty()){
                   NoDisplay(navigator = navigator)
                }else{
                    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
                        when (it) {
                            is UiState.Loading -> {
                                viewModel.getChildrenById(childrenId ?: listChild.first().id)
                            }

                            is UiState.Success -> {
                                val child = it.data
                                val gender = if (child.data.gender == "Laki-laki") "Boy" else "Girl"

                                var statusChildren: ChildrenStatusResponse? by remember {
                                    mutableStateOf(null)
                                }

                                LaunchedEffect(key1 = child) {
                                    val response = viewModel.getStatusChildren(
                                        dateToDay(child.data.birthDay),
                                        gender,
                                        child.data.growthHistory.first().weight,
                                        child.data.growthHistory.first().height,
                                    )
                                    statusChildren = response
                                }

                                if (statusChildren != null) {
                                    ChildrenTestScreen(
                                        navigator = navigator,
                                        allChildren = listChild,
                                        children = child,
                                        statusChildren = statusChildren!!,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            is UiState.Error -> {
                                showToast(it.errorMessage, context)
                            }
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
        modifier = modifier.fillMaxSize().padding(24.dp)
    ) {
        val (tvDataEmpty, btnAddChildren) = createRefs()
        Text(
            text = "Tidak Ada Data Anak, Silahkan Tambahkan Terlebih Dahulu",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
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

    var confirmDeleteChildren by remember {
        mutableStateOf(false)
    }

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

                    DropdownMenuItem(
                        text = { Text(text = "Delete Children") },
                        onClick = { /*TODO*/ })
                }
            }
        )


        if (confirmDeleteChildren) {
            AlertDialog(
                icon = {
                       Icon(
                           imageVector = Icons.Default.Delete,
                           contentDescription = null,
                           modifier = modifier.size(40.dp)
                       )
                },
                title = {
                        Text(text = "Kamu Yakin Ingin Menghapus Data Ini?")
                },
                text = {
                    Text(text = "Konfimarsi Pengahpusan")
                },
                dismissButton = {
                    confirmDeleteChildren = !confirmDeleteChildren
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                onDismissRequest = { confirmDeleteChildren = !confirmDeleteChildren }
            )
        }

        if (showListChildren) {
            ModalBottomSheet(onDismissRequest = { showListChildren = !showListChildren }) {
                LazyColumn {
                    items(allChildren, { it.id }) { child ->
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

                        CardChild(
                            children = child,
                            status = statusStunting,
                            modifier = modifier.clickable {
                                viewModel.getChildrenById(child.id)
                                showListChildren = !showListChildren
                            }
                        )
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
            Text(text = "Statistik Perkembangan",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = if (statisticRowSelected) {
                    Blue900
                } else {
                    Blue500
                },
                textAlign = TextAlign.Center,
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 16.dp))
                    .background(if (statisticRowSelected) Blue100 else Color.White)
                    .clickable {
                        statisticRowSelected = !statisticRowSelected
                    }
                    .weight(1f)
                    .padding(horizontal = 26.dp, vertical = 8.dp))
            Text(
                text = "Catatan Harian Anak",
                fontWeight = FontWeight.SemiBold,
                color = if (!statisticRowSelected) {
                    Blue900
                } else {
                    Blue500
                },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
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
                navigator = navigator,
                viewModel = viewModel
            )
        } else {
            DailyChildren(
                navigator = navigator,
                children = children,
                statusChildren = statusChildren,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun GrowthStatistic(
    children: DetailChildrenResponse,
    navigator: ChildrenScreenNavigator,
    statusChildren: ChildrenStatusResponse,
    viewModel: ChildrenViewModel,
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

        ChildrenDiagnosisSection(
            statusChildren = statusChildren,
            navigator = navigator,
            viewModel = viewModel
        )
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
                data = children.data.growthHistory.first().height.toInt().toString(),
                unit = "cm"
            )
            ChildrenBoxInfo(
                title = "Berat Badan",
                data = children.data.growthHistory.first().weight.toInt().toString(),
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
    var isHeight by remember {
        mutableStateOf(true)
    }
    val growthValue: Array<Float> = children.data.growthHistory.reversed().map {
        if (isHeight) it.height else it.weight
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
                .clip(RoundedCornerShape(16.dp))
                .background(Grey100)
                .padding(12.dp)
        ) {
            ProvideChartStyle(
                ChartStyle.fromColors(
                    axisLabelColor = LocalContentColor.current,
                    axisGuidelineColor = Color.Transparent,
                    axisLineColor = LocalContentColor.current,
                    entityColors = listOf(if (isHeight) Green700 else Yellow600),
                    elevationOverlayColor = Color.Cyan
                )
            ) {
                Chart(
                    chart = lineChart(),
                    model = chartEntryModel,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                )
            }

        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Button(
                onClick = { isHeight = true },
                colors = ButtonDefaults.buttonColors(containerColor = Green700)
            ) {
                Text(text = "Tinggi Badan")
            }
            Button(
                onClick = { isHeight = false },
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
    viewModel: ChildrenViewModel,
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
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 10.sp,
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
                    colors = CardDefaults.cardColors(
                        containerColor = when (statusChildren.stunting.message) {
                            "Stunting Akut" -> {
                                Color.Red
                            }

                            "Stunting" -> {
                                Yellow600
                            }

                            "Tinggi Normal" -> {
                                Green600
                            }

                            else -> {
                                Color.Gray
                            }
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.stunting.message,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Grey100,
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
                    fontSize = 10.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 10.sp,
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
                    colors = CardDefaults.cardColors(
                        containerColor = when (statusChildren.underweight.message) {
                            "Kurus Akut" -> {
                                Color.Red
                            }

                            "Kurus" -> {
                                Yellow600
                            }

                            "Berat Normal" -> {
                                Green600
                            }

                            else -> {
                                Color.Gray
                            }
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.underweight.message,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Grey100,
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
                    fontSize = 10.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = ":",
                    fontSize = 10.sp,
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
                    colors = CardDefaults.cardColors(
                        containerColor = when (statusChildren.wasted.message) {
                            "SAM", "Obesitas" -> {
                                Color.Red
                            }

                            "MAM", "Kelebihan Berat" -> {
                                Yellow600
                            }

                            "Gizi Normal" -> {
                                Green600
                            }

                            else -> {
                                Color.Gray
                            }
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusChildren.wasted.message,
                            fontSize = 10.sp,
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
    Card(
        modifier.padding(28.dp)
    ) {
        FoodRecommendation(
            viewModel = viewModel
        )
    }
}

@Composable
fun FoodRecommendation(
    viewModel: ChildrenViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        var foodRecommendation: FoodRecommendationResponse? by remember {
            mutableStateOf(null)
        }
        Text(text = "Rekomendasi Makanan", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

        LaunchedEffect(key1 = LocalContext.current) {
            val response = viewModel.getFoodRecommendation()
            foodRecommendation = response
        }

        if (foodRecommendation != null) {
            foodRecommendation?.name?.forEach {
                FoodRecommendationCard(foodName = it) {

                }
            }
        }

    }
}

@Composable
fun DailyChildren(
    children: DetailChildrenResponse,
    viewModel: ChildrenViewModel,
    statusChildren: ChildrenStatusResponse,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Blue100)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Update Terakhir: ${convertLongToDateString(children.data.growthHistory.first().createdAt)}",
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

                var childrenFoodData: ChildrenFoodResponse? by remember {
                    mutableStateOf(null)
                }

                var isError: Boolean by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = children.data.id) {
                    try {
                        childrenFoodData = viewModel.getChildrenFood2(children.data.id)
                    } catch (e: HttpException) {
                        val jsonInString = e.response()?.errorBody()?.string()
                        val errorBody =
                            Gson().fromJson(jsonInString, ChildrenFoodResponse::class.java)
                        val errorMessage = errorBody.message
                        showToast(errorMessage, context)
                        Log.d("Update Children", "Response: $e")
                        isError = true
                    }

                }

                if (isError) {
                    Column(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        FoodCard(
                            title = "Sarapan Pagi",
                            foodName = "Belum Diisi",
                            image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                            navigateToCamera = {
                                navigator.navigateToFoodClassification(
                                    children.data.id,
                                    "Makan Siang"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        FoodCard(
                            title = "Makan Siang",
                            foodName = "Belum Diisi",
                            image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                            navigateToCamera = {
                                navigator.navigateToFoodClassification(
                                    children.data.id,
                                    "Makan Siang"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        FoodCard(
                            title = "Makan Malam",
                            foodName = "Belum Diisi",
                            image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                            navigateToCamera = {
                                navigator.navigateToFoodClassification(
                                    children.data.id,
                                    "Makan Siang"
                                )
                            }
                        )
                    }
                } else {
                    if (childrenFoodData != null) {
                        val data = childrenFoodData!!.data
                        var makanSiang by remember {
                            mutableStateOf(true)
                        }

                        var makanMalam by remember {
                            mutableStateOf(true)
                        }

                        var sarapanPagi by remember {
                            mutableStateOf(true)
                        }
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            data.food.forEach {
                                if (it.schedule == "Sarapan Pagi") {
                                    sarapanPagi = false
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = it.schedule,
                                        foodName = it.foodName,
                                        image = it.imageUrl
                                            ?: "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            showToast("Anda Telah Mengisi Makanan Ini", context)
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = "Sarapan Pagi",
                                        foodName = "Belum Diisi",
                                        image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            navigator.navigateToFoodClassification(
                                                children.data.id,
                                                "Makan Siang"
                                            )
                                        }
                                    )
                                }

                                if (it.schedule == "Makan Siang") {
                                    makanSiang = false
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = it.schedule,
                                        foodName = it.foodName,
                                        image = it.imageUrl
                                            ?: "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            showToast("Anda Telah Mengisi Makanan Ini", context)
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = "Makan Siang",
                                        foodName = "Belum Diisi",
                                        image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            navigator.navigateToFoodClassification(
                                                children.data.id,
                                                "Makan Siang"
                                            )
                                        }
                                    )
                                }

                                if (it.schedule == "Makan Malam") {
                                    makanMalam = false
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = it.schedule,
                                        foodName = it.foodName,
                                        image = it.imageUrl
                                            ?: "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            showToast("Anda Telah Mengisi Makanan Ini", context)
                                        }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FoodCard(
                                        title = "Makan Malam",
                                        foodName = "Belum Diisi",
                                        image = "https://theme-assets.getbento.com/sensei/cc1b795.sensei/assets/images/catering-item-placeholder-704x520.png",
                                        navigateToCamera = {
                                            navigator.navigateToFoodClassification(
                                                children.data.id,
                                                "Makan Siang"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}