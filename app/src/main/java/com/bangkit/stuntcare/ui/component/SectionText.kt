package com.bangkit.stuntcare.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(text = title, fontWeight = FontWeight.Medium, fontSize = 16.sp, modifier = modifier)
}