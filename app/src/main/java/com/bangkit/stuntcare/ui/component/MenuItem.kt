package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.model.Menu
import com.bangkit.stuntcare.ui.model.dummyMenu
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    menu: Menu
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        Card(
            shape = CardDefaults.outlinedShape,
            modifier = modifier.size(60.dp, 60.dp)
        ) {
            Image(
                painter = painterResource(id = menu.imageMenu),
                contentDescription = null,
                modifier = modifier.fillMaxSize()
            )
        }
        Text(
            text = menu.title,
            fontWeight = FontWeight.Medium,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            modifier = modifier
                .padding(top = 8.dp)
                .width(width = 80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    StuntCareTheme {
        MenuItem(menu = dummyMenu.first())
    }
}