package com.bangkit.stuntcare.ui.view.parent.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.component.DisplayOnDevelopment
import com.bangkit.stuntcare.ui.component.SearchBar
import com.bangkit.stuntcare.ui.model.Article
import com.bangkit.stuntcare.ui.model.dummyArticle
import com.bangkit.stuntcare.ui.navigation.navigator.CommunityScreenNavigator
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator

@Composable
fun CommunityScreen(
    navigator: CommunityScreenNavigator,
    modifier: Modifier = Modifier
) {
    DisplayOnDevelopment()
}

@Composable
fun CommunityScreenContent(
    modifier: Modifier = Modifier,
    navigator: CommunityScreenNavigator
) {
    var tabIndex by remember {
        mutableIntStateOf(0)
    }
    val tabs = listOf("Article", "Post")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> {
                ArticleContent()
            }

            1 -> {

            }
        }
    }
}

@Composable
fun ArticleContent(
    modifier: Modifier = Modifier
) {
    Column {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            SearchBar(query = "", onQueryChange = {}, modifier = modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Filter",
                    modifier = modifier.size(32.dp)
                )
            }
        }
        LazyColumn {
            items(dummyArticle, { it.id }) {
                ArticleCard(article = it)
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(8.dp, 4.dp)) {
        Row(modifier = modifier.padding(8.dp, 4.dp)) {
            AsyncImage(
                model = article.image,
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(85.dp)
                    .padding(16.dp, 12.dp)
            )
            Column(modifier = modifier.padding(vertical = 16.dp)) {
                Text(text = article.author, fontWeight = FontWeight.ExtraLight, fontSize = 10.sp)
                Text(text = article.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = article.date,
                    fontWeight = FontWeight.Thin,
                    fontSize = 9.sp,
                    modifier = modifier.align(Alignment.Bottom)
                )
            }
        }
    }
}