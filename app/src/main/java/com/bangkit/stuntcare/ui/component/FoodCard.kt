package com.bangkit.stuntcare.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.theme.Blue600
import com.bangkit.stuntcare.ui.theme.Blue900
import com.bangkit.stuntcare.ui.theme.Green600
import com.bangkit.stuntcare.ui.theme.Grey100
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun FoodCard(
    title: String = "",
    image: String = "",
    foodName: String = "",
    foodContent: String = "",
    navigateToCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .background(Grey100)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val (imgFood, tvTitle, tvTypeFood, tvFoodName, tvNutrition, btnDetailNutrition, imgPhoto) = createRefs()
            AsyncImage(model = image,
                contentDescription = null,
                modifier = modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(imgFood) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    })
            Text(text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Green600,
                modifier = modifier.constrainAs(tvTitle) {
                    start.linkTo(imgFood.end, margin = 16.dp)
                    top.linkTo(parent.top)
                })
            Text(text = "Jenis Makanan",
                fontSize = 10.sp,
                color = Blue900,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.constrainAs(tvTypeFood) {
                    top.linkTo(tvTitle.bottom, margin = 8.dp)
                    start.linkTo(tvTitle.start)
                })

            Text(text = foodName,
                fontSize = 10.sp,
                color = Blue900,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.constrainAs(tvFoodName) {
                    top.linkTo(tvTypeFood.top)
                    start.linkTo(tvTypeFood.end, margin = 40.dp)
                    bottom.linkTo(tvTypeFood.bottom)
                })

            Text(text = "Kandungan Nutrisi",
                fontSize = 10.sp,
                color = Blue900,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.constrainAs(tvNutrition) {
                    top.linkTo(tvTypeFood.bottom, margin = 8.dp)
                    start.linkTo(tvTypeFood.start)
                })

            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(Blue600),
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth, minHeight = 10.dp
                    )
                    .constrainAs(btnDetailNutrition) {
                        top.linkTo(tvNutrition.top)
                        start.linkTo(tvNutrition.end, margin = 12.dp)
                        bottom.linkTo(tvNutrition.bottom)
                    }
            ) {
                Text(
                    text = "Detail",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }

            IconButton(
                onClick = { navigateToCamera() },
                modifier = modifier
                    .constrainAs(imgPhoto){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(btnDetailNutrition.end, margin = 8.dp)
                    }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FoodCardPreview() {
    StuntCareTheme {
        FoodCard(title = "Pagi",
            image = "",
            foodName = "Nasi Goreng",
            foodContent = "Apa saja",
            navigateToCamera = {})
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FoodCardPreviewWhenDataNotExist() {
    StuntCareTheme {
        FoodCard(navigateToCamera = {})
    }
}