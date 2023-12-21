package com.bangkit.stuntcare.ui.view.parent.consultation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.data.remote.response.DetailDoctorData
import com.bangkit.stuntcare.data.remote.response.DetailDoctorResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.theme.Blue600
import com.bangkit.stuntcare.ui.theme.Blue900
import com.bangkit.stuntcare.ui.theme.Green100
import com.bangkit.stuntcare.ui.theme.Red900
import com.bangkit.stuntcare.ui.view.ViewModelFactory

@Composable
fun DetailDoctorScreen(
    doctorId: String,
    modifier: Modifier = Modifier,
    navigator: ConsultationScreenNavigator,
    viewModel: DetailDoctorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    viewModel.doctor.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getDoctorById(doctorId)
            }

            is UiState.Success -> {
                val data = it.data
                DetailDoctorContent(doctor = data, navigator = navigator, modifier = modifier)
            }

            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDoctorContent(
    doctor: DetailDoctorResponse,
    navigator: ConsultationScreenNavigator,
    modifier: Modifier
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Informasi Dokter",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = modifier.size(24.dp)
                    )
                }
            }
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            HeaderSection(doctor = doctor.data)
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green100,
                        contentColor = Blue900
                    ),
                    modifier = modifier.weight(0.5f)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_money), contentDescription = null)
                    Text(
                        text = "Rp. ${doctor.data.price} / Jam",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = modifier.padding(horizontal = 12.dp)
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green100,
                        contentColor = Blue900
                    ),
                    modifier = modifier.weight(0.5f)
                ) {
                    for (i in 0..4) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow
                        )
                    }
                    Text(text = "150", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
            Card {

            }
        }

    }
}

@Composable
fun HeaderSection(
    doctor: DetailDoctorData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 20.dp)
        ) {
            AsyncImage(
                model = doctor.imageUrl, contentDescription = null, modifier = modifier
                    .size(80.dp)
                    .padding(12.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Online",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.Green
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Text(text = doctor.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hospital),
                    contentDescription = null,
                    modifier = modifier.size(20.dp)
                )
                Text(text = doctor.hospital, fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "No. STR \t\t\t: ${doctor.noStr}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Pengalaman \t: ${doctor.experience}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

    }
}