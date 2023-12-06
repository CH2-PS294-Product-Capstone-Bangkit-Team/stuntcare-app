package com.bangkit.stuntcare.ui.view.consultation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.SearchBar
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.model.dummyDoctor
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.view.ViewModelFactory

@Composable
fun ConsultationScreen(
    modifier: Modifier = Modifier,
    navigator: ConsultationScreenNavigator,
    viewModel: ConsultationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllDoctor()
            }

            is UiState.Success -> {
                val doctor = it.data
                ConsultationContent(doctor = doctor, onQueryChange = viewModel::searchDoctor, navigator = navigator, query = query)
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun ConsultationContent(
    navigator: ConsultationScreenNavigator,
    onQueryChange: (String) -> Unit,
    query: String,
    doctor: List<Doctor>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                SearchBar(query = query, onQueryChange = onQueryChange, modifier.weight(0.9f))
                IconButton(onClick = { navigator.navigateToChatScreen() }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = modifier
                            .size(60.dp)
                            .padding(start = 4.dp, end = 8.dp)
                            .weight(0.1f)
                    ) // TODO = Ganti jadi Icon Chat
                }
            }
        }

        items(doctor, { it.id }) {
            DoctorCard(doctor = it, navigator = navigator, modifier = modifier.clickable { navigator.navigateToDetailDoctor(it.id) })
        }
    }
}

@Composable
fun DoctorCard(
    doctor: Doctor,
    navigator: ConsultationScreenNavigator,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            AsyncImage(
                model = doctor.image,
                contentDescription = null,
                modifier = modifier.size(width = 65.dp, height = 77.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = doctor.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text(text = doctor.type, fontWeight = FontWeight.ExtraLight, fontSize = 10.sp)
                Card(modifier = modifier.padding(top = 4.dp)) {
                    Text(
                        text = "${doctor.longExperience} Tahun",
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = 8.sp
                    )
                }
                Text(text = doctor.price, fontWeight = FontWeight.ExtraLight, fontSize = 8.sp)
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
                modifier = modifier.fillMaxSize()
            ) {
                Box(modifier = modifier) {

                }
                Button(
                    onClick = { navigator.navigateToSetSchedule(doctor.id) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = modifier
                        .size(height = 20.dp, width = 60.dp)
                ) {
                    Text(
                        text = "Chat",
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = modifier.padding(0.dp)
                    )
                }
            }
        }
    }
}
