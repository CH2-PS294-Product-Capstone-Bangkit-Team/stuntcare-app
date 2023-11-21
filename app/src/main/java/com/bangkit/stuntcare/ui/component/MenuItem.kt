package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.model.Menu
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    menu: Menu
) {
    Card(
        shape = CardDefaults.outlinedShape,
        modifier = modifier.size(90.dp, 100.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = menu.imageMenu),
                contentDescription = null,
                modifier = modifier.size(70.dp,70.dp)
            )
            Text(text = menu.title)
        }
    }
}

@Preview (showBackground = true)
@Composable
fun MenuItemPreview() {
    StuntCareTheme {
        MenuItem(menu = Menu(R.drawable.ic_child_menu, "Menu"))
    }
}