package com.bangkit.stuntcare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.model.Article
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = modifier
            .width(200.dp)
            .height(160.dp)
            .padding(horizontal = 4.dp)
    ) {
        Box(modifier = modifier) {
            Column {
                AsyncImage(
                    model = article.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(92.dp)
                        .fillMaxWidth()
                )
                Column(
                    modifier = modifier.padding(horizontal = 8.dp).padding(top = 4.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                    )
                    Text(text = article.date, fontSize = 8.sp, fontWeight = FontWeight.Light)
                    Text(
                        text = article.description,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    StuntCareTheme {
        ArticleItem(
            article = Article(
                "adjsada",
                "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/09/05032113/Stunting.jpg",
                "Udin",
                "Sehat Sehat Lelaki Kecil Ayah",
                "24 Aug",
                "Wahyuddin"
            )
        )
    }
}