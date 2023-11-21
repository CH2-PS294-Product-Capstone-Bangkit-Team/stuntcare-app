package com.bangkit.stuntcare.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.ui.component.ArticleItem
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.MenuItem
import com.bangkit.stuntcare.ui.model.dummyArticle
import com.bangkit.stuntcare.ui.model.dummyMenu

@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    navigateToProfilePage: () -> Unit,
    navigateToNotificationPage: () -> Unit,
    navigateToChildPage: (Int) -> Unit,
    navigateToMenu: (Int) -> Unit,
    navigateToArticlePage: () -> Unit,
    navigateToDetailArticle: (Long) -> Unit,
    navigateToPostPage: () -> Unit,
    navigateToDetailPostPage: (Long) -> Unit
) {
    HomePageContent(
        navigateToProfile = navigateToProfilePage,
        navigateToNotification = navigateToNotificationPage,
        navigateToChildPage = navigateToChildPage,
        navigateToMenu = navigateToMenu,
        navigateToArticlePage = navigateToArticlePage,
        navigateToDetailArticle = navigateToDetailArticle,
        navigateToPostPage = navigateToPostPage,
        navigateToDetailPostPage = navigateToDetailPostPage
    )
}


@Composable
fun HomePageContent(
    modifier: Modifier = Modifier,
    navigateToProfile: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToChildPage: (Int) -> Unit,
    navigateToMenu: (Int) -> Unit,
    navigateToArticlePage: () -> Unit,
    navigateToDetailArticle: (Long) -> Unit,
    navigateToPostPage: () -> Unit,
    navigateToDetailPostPage: (Long) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TopBarSection(
            navigateToProfile = navigateToProfile,
            navigateToNotification = navigateToNotification,
            modifier = modifier
        )
        ChildListSection(navigateToChildPage = navigateToChildPage)
        MenuItemSection(navigateToMenu = navigateToMenu)
        ArticleStuntingSection(
            navigateToArticlePage = navigateToArticlePage,
            navigateToDetailArticle = navigateToDetailArticle
        )
        PostSection(
            navigateToPostPage = navigateToPostPage,
            navigateToDetailPostPage = navigateToDetailPostPage
        )
    }
}

@Composable
fun TopBarSection(
    navigateToProfile: () -> Unit,
    navigateToNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp))
            .background(Color.Green)
            .padding(0.dp, 0.dp, 0.dp, 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_child_menu), // Ganti Jadi Logo
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.Top)
                    .size(48.dp)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.welcome_homepage, "Siang", "Wahyu"),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            IconButton(onClick = navigateToNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    modifier = modifier.size(28.dp)
                )
            }

            IconButton(onClick = navigateToProfile) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun ChildListSection(
    navigateToChildPage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp)
    ) {
        CardChild(navigateToChildPage = { navigateToChildPage(1) })  // Tambahakan Parameter Id nya
    }
}

@Composable
fun MenuItemSection(
    navigateToMenu: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(dummyMenu, { it.title }) {
            MenuItem(
                menu = it,
                modifier = modifier.clickable { navigateToMenu(1) }) // Tambahakan Parameter Id nya
        }
    }
}


@Composable
fun ArticleStuntingSection(
    navigateToArticlePage: () -> Unit,
    navigateToDetailArticle: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Artikel Stunting", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Text(text = "Lihat Semua", fontWeight = FontWeight.Light, fontSize = 12.sp)
                IconButton(onClick = navigateToArticlePage) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
        LazyRow {
            items(dummyArticle, { it.title }) {
                ArticleItem(
                    article = it,
                    modifier.clickable { navigateToDetailArticle(1) }) // Tambahkan parameter id nya
            }
        }
    }
}

@Composable
fun PostSection(
    navigateToPostPage: () -> Unit,
    navigateToDetailPostPage: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Postingan Terpopuler", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Text(text = "Lihat Semua", fontWeight = FontWeight.Light, fontSize = 12.sp)
                IconButton(onClick = navigateToPostPage) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
        LazyRow {
            items(dummyArticle, { it.title }) {
                ArticleItem(
                    article = it,
                    modifier = modifier.clickable { navigateToDetailPostPage(1) })  // Tambahakan Parameter Id nya
            }
        }
    }
}
