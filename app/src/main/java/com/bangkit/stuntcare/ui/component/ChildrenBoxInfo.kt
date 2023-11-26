package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildrenBoxInfo(
    title: String,
    data: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(95.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
    ) {
        Text(text = title, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        Divider(color = Color.Black, thickness = 1.dp)
        Text(text = data, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text(text = unit, fontWeight = FontWeight.Light, fontSize = 10.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ChildrenBoxInfoPreview() {
    StuntCareTheme {
        ChildrenBoxInfo(title = "Berat", data = "4.3", unit = "Kg")
    }
}