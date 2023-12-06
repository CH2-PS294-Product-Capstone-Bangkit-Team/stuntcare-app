package com.bangkit.stuntcare.ui.view.consultation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory

@Composable
fun DetailDoctorScreen(
    doctorId: Int,
    modifier: Modifier = Modifier,
    navigator: ConsultationScreenNavigator,
    viewModel: DetailDoctorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.getDoctorById(doctorId).let {
        val data = viewModel.doctor.value
        if (data != null) {
            DetailDoctorContent(doctor = data, navigator = navigator, modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDoctorContent(
    doctor: Doctor,
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
        AsyncImage(
            model = doctor.image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .size(180.dp)
        )
        Column (
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){

            Text(text = doctor.name, fontWeight = FontWeight.Medium, fontSize = 24.sp)
            Text(text = doctor.type, fontWeight = FontWeight.ExtraLight, fontSize = 16.sp)
            Text(text = "${doctor.longExperience.toString()} Tahun", fontWeight = FontWeight.ExtraLight, fontSize = 14.sp)
            InformationDoctorRow(text = doctor.university, icon = Icons.Default.AccountCircle)
            InformationDoctorRow(text = doctor.hospital, icon = Icons.Default.AccountCircle)
            InformationDoctorRow(text = doctor.strNumber, icon = Icons.Default.AccountCircle)
            InformationDoctorRow(text = doctor.price, icon = Icons.Default.AccountCircle)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()
            ) {
                Button(onClick = { navigator.navigateToSetSchedule(doctor.id) }, modifier = modifier.align(Alignment.Bottom).padding(16.dp)) {
                    Text(text = "Atur Jadwal", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun InformationDoctorRow(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row {
        Icon(imageVector = icon, contentDescription = null, modifier = modifier.size(32.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = modifier.padding(horizontal = 20.dp)
        )
    }
}