package com.bangkit.stuntcare.ui.component.buuble_chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.ui.theme.Blue900

@Composable
fun BubbleChatFromSender(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
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