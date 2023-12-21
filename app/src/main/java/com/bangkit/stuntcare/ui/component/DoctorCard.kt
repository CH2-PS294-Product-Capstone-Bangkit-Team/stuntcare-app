package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.DoctorItem
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.theme.Blue200
import com.bangkit.stuntcare.ui.theme.Blue50
import com.bangkit.stuntcare.ui.theme.Blue600
import com.bangkit.stuntcare.ui.theme.Blue900
import com.bangkit.stuntcare.ui.theme.Red900
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun DoctorCard(
    doctor: DoctorItem,
    navigator: ConsultationScreenNavigator,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(contentColor = Blue900, containerColor = Blue50),
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(150.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
        ){
            AsyncImage(
                model = doctor.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .fillMaxHeight()
                    .clip(CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Text(text = doctor.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Dokter Anak", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Blue600)
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
                        modifier = modifier.size(16.dp)
                    )
                    Text(text = doctor.hospital, fontSize = 10.sp, fontWeight = FontWeight.Normal)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "No. STR \t\t\t: ${doctor.noStr}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Pengalaman \t: ${doctor.experience}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(text = "Fee: Rp. ${doctor.price}", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Red900)
                }
            }

        }
    }
}