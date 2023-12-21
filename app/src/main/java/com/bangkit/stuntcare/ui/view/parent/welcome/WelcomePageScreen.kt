package com.bangkit.stuntcare.ui.view.parent.welcome

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.model.ImageSlider
import com.bangkit.stuntcare.ui.theme.Blue200
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.android.engage.common.datamodel.Image
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomePageScreen(
    navigateToLoginScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState()

    val listImageSlider = listOf(
        ImageSlider(
            R.drawable.img_slider_1,
            stringResource(R.string.slider_1),
            stringResource(R.string.description_1)
        ),
        ImageSlider(
            R.drawable.img_slider_2,
            stringResource(R.string.slider_2),
            stringResource(R.string.description_2)
        ),
        ImageSlider(
            R.drawable.img_slider_3,
            stringResource(R.string.slider_3),
            stringResource(R.string.description_3)
        ),
        ImageSlider(
            R.drawable.img_slider_4,
            stringResource(R.string.slider_4),
            stringResource(R.string.description_4)
        )
    )
    ConstraintLayout {
        val (imgPager, dotPager, btnLogin, btnRegister) = createRefs()
        HorizontalPager(
            count = listImageSlider.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(imgPager) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) { index ->
            Column {
                Image(
                    painter = painterResource(id = listImageSlider[index].image),
                    contentDescription = "this is image from server",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )

                Text(
                    text = listImageSlider[index].title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                Text(
                    text = listImageSlider[index].description,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }
        Row(
            Modifier
                .height(10.dp)
                .fillMaxWidth()
                .constrainAs(dotPager) {
                    start.linkTo(parent.start)
                    top.linkTo(imgPager.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(listImageSlider.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Blue200 else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp, bottom = 5.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .constrainAs(btnLogin){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ) {
            Button(onClick = { navigateToLoginScreen() }) {
                Text("Masuk")
            }
            Button(onClick = { navigateToRegisterScreen() }) {
                Text("Daftar")
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
}