package com.bangkit.stuntcare.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.ui.theme.StuntCareTheme
import kotlin.math.round
import kotlin.math.roundToInt
import androidx.compose.ui.Alignment
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun QuadLineChartComponent(
) {

    val xValue: Array<Float> = dummyLineChart.map {
        it.xValue
    }.toTypedArray()

    val yValue: Array<Float> = dummyLineChart.map {
        it.yValue
    }.toTypedArray()

    val chartEntryModel = entryModelOf(entriesOf(*xValue), entriesOf(*yValue))
    Chart(
        chart = lineChart(),
        model = chartEntryModel,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis()
    )
}

data class ChartDataImpl(
    val chartString: String,
    val xValue: Float,
    val yValue: Float
)

val dummyLineChart = listOf(
    ChartDataImpl("0.1 Tahun", 11F, 48F),
    ChartDataImpl("0.2 Tahun", 12F, 50F),
    ChartDataImpl("0.3 Tahun", 13F, 51F),
    ChartDataImpl("0.4 Tahun", 14F, 55F),
    ChartDataImpl("0.5 Tahun", 15F, 57F)
)

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LineChartComponenetPreview() {
    StuntCareTheme {
        QuadLineChartComponent(
        )
    }
}
