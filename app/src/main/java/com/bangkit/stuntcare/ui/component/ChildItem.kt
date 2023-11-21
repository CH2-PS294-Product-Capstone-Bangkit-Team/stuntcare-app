package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.stuntcare.R

@Composable
fun CardChild(
    navigateToChildPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .size(250.dp, 89.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.removebg_preview), // Ganti ke dummy data dari model
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(text = "Wahyuddin")
                Text(text = "1 Tahun, 3 Bulan", fontWeight = FontWeight.Light)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardChildPreview() {
    CardChild(navigateToChildPage = { })
}