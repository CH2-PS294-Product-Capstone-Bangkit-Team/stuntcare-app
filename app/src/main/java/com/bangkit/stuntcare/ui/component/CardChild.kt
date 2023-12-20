package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.ui.theme.Blue600
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.bangkit.stuntcare.ui.view.parent.home.HomeViewModel

@Composable
fun CardChild(
    children: ChildItem,
    status: String,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel =
        viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .padding(8.dp, 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .height(150.dp)
            .border(
                width = 1.dp,
                color = Blue600,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = modifier.wrapContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = children.name, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(70.dp)
                ) {
                    AsyncImage(
                        model = children.imageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = modifier
                            .size(60.dp)
                            .padding(end = 4.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "Tanggal Lahir: ${children.birthDay}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 8.sp
                        )
                        Text(
                            text = "Jenis Kelamin: ${children.gender}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 8.sp
                        )
                        Text(
                            text = "Tinggi: ${children.height} Cm",
                            fontWeight = FontWeight.Medium,
                            fontSize = 8.sp
                        )
                        Text(
                            text = "Berat: ${children.weight} Kg",
                            fontWeight = FontWeight.Medium,
                            fontSize = 8.sp
                        )
                    }
                }
                Row {
                    Text(
                        text = "Status Stunting:",
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        modifier = modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = status,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = Color.Red
                    )

                }
            }
        }
    }
}