package com.bangkit.stuntcare.ui.view.children.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ChildrenBoxInfo
import com.bangkit.stuntcare.ui.component.FoodCard
import com.bangkit.stuntcare.ui.component.QuadLineChartComponent
import com.bangkit.stuntcare.ui.component.SectionText
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory

@Composable
fun ChildrenScreen(
    viewModel: ChildrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigator: ChildrenScreenNavigator
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllChildren()
            }

            is UiState.Success -> {
                val data = it.data
                ChildrenContent(
                    navigator = navigator,
                    children = data,
                    viewModel = viewModel
                )
            }

            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun ChildrenContent(
    modifier: Modifier = Modifier,
    children: List<Children>,
    navigator: ChildrenScreenNavigator,
    viewModel: ChildrenViewModel
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var childrenId by remember {
        mutableStateOf(1)
    }

    var tabIndex by remember {
        mutableStateOf(0)
    }

    val tabs = listOf("STATISTIK", "DIARY ANAK")

    val childById = viewModel.children.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.clickable {
                    expanded = !expanded
                }
            ) {
                Text(
                    text = childById!!.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "More"
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    children.map {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                childrenId = it.id
                                viewModel.getChildrenById(childrenId)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Text(
            text = "Status",
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            modifier = modifier.padding(top = 4.dp)
        )
        ChildData(
            children = childById,
            navigator = navigator,
            modifier = modifier
        )
        TabRow(
            selectedTabIndex = tabIndex, modifier = modifier
                .offset(0.dp, 0.dp)
                .padding(top = 8.dp)
                .background(Color.Transparent)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        Column() {
            when (tabIndex) {
                0 -> StatisticChildrenScreen(navigator)
                1 -> ChildrenDiary()
            }
        }

    }
}

@Composable
fun ChildData(
    children: Children?,
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    if (children != null) {
        Column(modifier = modifier.padding(top = 16.dp)) {
            Text(
                text = "Data Anak",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = modifier.padding(8.dp)
            )
            Card(
                modifier = modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    ChildrenBoxInfo(title = "Berat", data = children.weight.toString(), unit = "Kg")
                    ChildrenBoxInfo(
                        title = "Tinggi",
                        data = children.height.toString(),
                        unit = "Kg"
                    )
                    ChildrenBoxInfo(title = "Umur", data = children.age.toString(), unit = "Kg")
                }
                Divider(thickness = 1.dp)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Last Updated: 12-12-2024",
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                        modifier = modifier
                            .padding(top = 8.dp)
                    )
                    Button(
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
                        onClick = { navigator.navigateToUpdateData(children.id) }
                    ) {
                        Text(text = "Update", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticChildrenScreen(
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Statistik Perkembangan Anak",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = modifier.padding(vertical = 8.dp)
        )
        Card {
            QuadLineChartComponent() // TODO: Perbaiki Componentnya

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = modifier.size(24.dp)
                    )
                    Text(text = "Stunting: ", fontWeight = FontWeight.Light, fontSize = 12.sp)
                    Text(
                        text = "Pendek",
                        color = Color.Red,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = modifier.size(24.dp)
                    )
                    Text(text = "BMI: ", fontWeight = FontWeight.Light, fontSize = 12.sp)
                    Text(
                        text = "Kurus",
                        color = Color.Red,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                }

            }
        }

        Text(text = "Rekomendasi Makanan", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
//        LazyRow{
//            // TODO: Tambahkan Card Makanan
//        }

    }
}

@Composable
fun ChildrenDiary(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SectionText(title = "Hari Ini", modifier = modifier.padding(vertical = 8.dp))
        Text(text = "Protein", fontWeight = FontWeight.Light, fontSize = 14.sp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(progress = 0.5f)
            Text(text = "250/500")
        }
        Text(text = "Karbohidrat", fontWeight = FontWeight.Light, fontSize = 14.sp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(progress = 0.5f)
            Text(text = "250/500")
        }

        FoodCard(
            title = "Pagi",
            image = "https://storage.googleapis.com/flip-prod-mktg-strapi/media-library/makanan_khas_malaysia_c4324d6c08/makanan_khas_malaysia_c4324d6c08.jpeg",
            foodName = "Nasi Goreng",
            foodContent = "Protein Alami",
            navigateToCamera = { /*TODO*/ }
        )
        FoodCard(
            title = "Siang",
            image = "https://storage.googleapis.com/flip-prod-mktg-strapi/media-library/makanan_khas_malaysia_c4324d6c08/makanan_khas_malaysia_c4324d6c08.jpeg",
            foodName = "Nasi Goreng",
            foodContent = "Protein Alami",
            navigateToCamera = { /*TODO*/ }
        )
        FoodCard(
            title = "Malam",
            navigateToCamera = { /*TODO*/ }
        )
    }
}