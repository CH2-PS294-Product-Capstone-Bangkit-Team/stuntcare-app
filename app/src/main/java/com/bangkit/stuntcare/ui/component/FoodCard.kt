package com.bangkit.stuntcare.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun FoodCard(
    title: String = "",
    image: String = "",
    foodName: String = "",
    foodContent: String = "",
    navigateToCamera: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (foodName.isNullOrEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(125.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = modifier.padding(top = 16.dp)
                )
                Button(
                    onClick = { navigateToCamera(1) },
                    modifier = modifier
                        .padding(horizontal = 48.dp, vertical = 8.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.take_food_image),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .size(260.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = modifier.padding(top = 16.dp)
                )
                AsyncImage(
                    model = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .size(160.dp)
                        .padding(16.dp)
                )
                Column(
                    modifier = modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(), horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = foodName,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = modifier.padding(bottom = 8.dp)
                    )
                    Text(text = foodContent, fontWeight = FontWeight.Light, fontSize = 12.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FoodCardPreview() {
    StuntCareTheme {
        FoodCard(
            title = "Pagi",
            image = "",
            foodName = "Nasi Goreng",
            foodContent = "Apa saja",
            navigateToCamera = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FoodCardPreviewWhenDataNotExist() {
    StuntCareTheme {
        FoodCard(
            navigateToCamera = {}
        )
    }
}