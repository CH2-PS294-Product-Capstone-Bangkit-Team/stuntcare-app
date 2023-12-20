package com.bangkit.stuntcare.ui.component

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.theme.Blue600
import com.bangkit.stuntcare.ui.theme.Grey100
import com.bangkit.stuntcare.ui.theme.ProgressColor
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import com.bangkit.stuntcare.ui.utils.removeTrailingZeros

@Composable
fun NutritionIndicator(
    title: String,
    currentValue: Float,
    maximumValue: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Blue600
            )
            Text(
                text = stringResource(
                    R.string.range_nutrition,
                    removeTrailingZeros(currentValue),
                    removeTrailingZeros(maximumValue)
                ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Blue600
            )
        }
        LinearProgressIndicator(
            progress = currentValue / maximumValue,
            modifier = modifier.fillMaxWidth(),
            color = ProgressColor,
            trackColor = Grey100
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NutritionIndicatorPreview() {
    StuntCareTheme {
        NutritionIndicator("Karbohidrat", 250f, 500f)
    }
}