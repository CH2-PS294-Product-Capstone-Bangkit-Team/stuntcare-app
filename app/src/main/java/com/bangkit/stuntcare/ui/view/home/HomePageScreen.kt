package com.bangkit.stuntcare.ui.view.home

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ArticleItem
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.MenuItem
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.model.dummyArticle
import com.bangkit.stuntcare.ui.model.dummyMenu
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory

@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    homePageScreenNavigator: HomePageScreenNavigator,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllChildren()
            }

            is UiState.Success -> {
                val data = it.data
                HomePageContent(
                    homePageScreenNavigator = homePageScreenNavigator,
                    data = data
                )
            }

            is UiState.Error -> {}
        }
    }

}


@Composable
fun HomePageContent(
    modifier: Modifier = Modifier,
    homePageScreenNavigator: HomePageScreenNavigator,
    data: List<Children>
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TopBarSection(
            navigateToProfile = { homePageScreenNavigator.navigateToProfilePage() },
            navigateToNotification = { homePageScreenNavigator.navigateToNotificationPage() },
            modifier = modifier
        )
        ChildListSection(navigateToChildPage = { homePageScreenNavigator.navigateToChildPage() }, data = data)
        MenuItemSection(navigateToMenu = {  })
        ArticleStuntingSection(
            homePageScreenNavigator = homePageScreenNavigator
        )
        PostSection(
            homePageScreenNavigator = homePageScreenNavigator
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
    data: List<Children>,
    modifier: Modifier = Modifier
) {
    LazyRow {
        items(data, { it.id }) {
            CardChild(
                modifier = modifier.clickable { navigateToChildPage(it.id) },
                navigateToChildPage = { navigateToChildPage(it.id) },
                name = it.name,
                image = it.image,
                age = it.age.toString()
            )
        }
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
                modifier = modifier.clickable { navigateToMenu(it.imageMenu) }) // Tambahakan Parameter Id nya
        }
    }
}


@Composable
fun ArticleStuntingSection(
    homePageScreenNavigator: HomePageScreenNavigator,
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
                IconButton(onClick = { homePageScreenNavigator.navigateToArticlePage() }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
        LazyRow {
            items(dummyArticle, { it.id }) {
                ArticleItem(
                    article = it,
                    modifier.clickable { homePageScreenNavigator.navigateToDetailArticle(it.image.toLong()) }) // Tambahkan parameter id nya
            }
        }
    }
}

@Composable
fun PostSection(
    homePageScreenNavigator: HomePageScreenNavigator,
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
                IconButton(onClick = { homePageScreenNavigator.navigateToPostPage() }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
        LazyRow {
            items(dummyArticle, { it.id }) {
                ArticleItem(
                    article = it,
                    modifier = modifier.clickable { homePageScreenNavigator.navigateToDetailPostPage(it.image.toLong()) })  // Tambahakan Parameter Id nya
            }
        }
    }
}
