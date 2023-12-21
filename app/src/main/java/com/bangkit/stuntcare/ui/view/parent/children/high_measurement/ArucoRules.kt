package com.bangkit.stuntcare.ui.view.parent.children.high_measurement

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.navigation.Screen
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AruCoRulesScreen(
    navigator: ChildrenScreenNavigator,
    modifier: Modifier = Modifier
) {
    val text = buildAnnotatedString {
        append(stringResource(id = R.string.step_1))
        pushStringAnnotation(tag = "toChrome", annotation = "tochrome")
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = Color.Red,
                fontSize = 20.sp
            )
        ) {
            append(" (Dokumen AruCo Marker)")
        }
    }
    val uriHandler = LocalUriHandler.current

    Column {
        TopAppBar(
            title = {
                Text("Tutorial Pengambilan Foto")
            },
            navigationIcon = {
                IconButton(onClick = { navigator.backNavigation() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState())
        ) {
            ClickableText(
                text = text,
                onClick = {
                    text.getStringAnnotations(tag = "toGuide", start = it, end = it).firstOrNull()
                        .let {
                            uriHandler.openUri("https://drive.google.com/file/d/1q3_AQtYjb9V-AU71yyQ5Hx0CjloZQdZ9/view?usp=drive_link")
                        }
                },
                style = TextStyle(
                    textAlign = TextAlign.Justify
                )
            )
            Text(text = stringResource(id = R.string.step_2), textAlign = TextAlign.Justify, fontSize = 20.sp)
            Image(painter = painterResource(id = R.drawable.image_1), contentDescription = null, modifier = modifier.size(180.dp))
            Text(text = stringResource(id = R.string.step_3), textAlign = TextAlign.Justify, fontSize = 20.sp)
            Image(painter = painterResource(id = R.drawable.image_2), contentDescription = null, modifier = modifier.size(180.dp))
        }


    }
}