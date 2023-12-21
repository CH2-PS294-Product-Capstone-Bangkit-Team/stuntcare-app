package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.ui.theme.Blue500
import com.bangkit.stuntcare.ui.theme.Blue800
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun ChildrenBoxInfo(
    title: String,
    data: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .size(height = 88.dp, width = 80.dp)
                .background(Blue500)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .background(Blue800)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }

            Text(
                text = data,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
            Text(
                text = unit,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Preview
@Composable
fun ChildrenBoxInfoPreview() {
    StuntCareTheme {
        ChildrenBoxInfo(title = "Berat", data = "4.3", unit = "Kg")
    }
}