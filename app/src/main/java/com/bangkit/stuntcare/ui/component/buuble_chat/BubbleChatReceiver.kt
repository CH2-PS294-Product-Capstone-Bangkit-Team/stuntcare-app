package com.bangkit.stuntcare.ui.component.buuble_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.theme.Blue900

@Composable
fun BubbleChatReceiver(
    text: String,
    image: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier.size(36.dp)
        )

        Card(
            shape = RoundedCornerShape(topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Blue900, contentColor = Color.White),
            modifier = modifier
                .wrapContentSize()
                .padding(start = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = text, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
        }
    }
}