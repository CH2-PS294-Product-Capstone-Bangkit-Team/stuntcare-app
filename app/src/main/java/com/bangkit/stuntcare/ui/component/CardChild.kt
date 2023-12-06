package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CardChild(
    name: String,
    age: String,
    image: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .padding(8.dp, 16.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .size(250.dp, 89.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = modifier
                    .padding(16.dp)
                    .size(70.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(text = name)
                Text(text = age, fontWeight = FontWeight.Light)
            }
        }
    }
}