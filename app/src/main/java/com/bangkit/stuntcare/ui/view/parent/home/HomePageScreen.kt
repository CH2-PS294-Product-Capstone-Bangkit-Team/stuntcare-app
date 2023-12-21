package com.bangkit.stuntcare.ui.view.parent.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.stuntcare.R
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.component.ArticleItem
import com.bangkit.stuntcare.ui.component.CardChild
import com.bangkit.stuntcare.ui.component.MenuItem
import com.bangkit.stuntcare.ui.model.dummyArticle
import com.bangkit.stuntcare.ui.model.dummyMenu
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator
import com.bangkit.stuntcare.ui.theme.Blue100
import com.bangkit.stuntcare.ui.theme.Blue500
import com.bangkit.stuntcare.ui.theme.Grey100
import com.bangkit.stuntcare.ui.utils.dateToDay
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    homePageScreenNavigator: HomePageScreenNavigator,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
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
                    data = data,
                    viewModel = viewModel,
                    modifier = modifier
                )

            }

            is UiState.Error -> {}
        }
    }

}


@Composable
fun HomePageContent(
    homePageScreenNavigator: HomePageScreenNavigator,
    viewModel: HomeViewModel,
    data: List<ChildItem>,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .background(Blue500)
                .offset(y = 16.dp)
        ) {
            TopBarSection(
                navigateToProfile = { homePageScreenNavigator.navigateToProfilePage() },
                navigateToNotification = { homePageScreenNavigator.navigateToNotificationPage() },
                modifier = modifier
            )
        }
        ChildListSection(
            homePageScreenNavigator = homePageScreenNavigator,
            data = data,
            viewModel = viewModel
        )
        MenuItemSection(navigateToMenu = { })
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Grey100)
        ) {
            ArticleStuntingSection(
                homePageScreenNavigator = homePageScreenNavigator
            )
            PostSection(
                homePageScreenNavigator = homePageScreenNavigator
            )
        }
    }
}

@Composable
fun TopBarSection(
    navigateToProfile: () -> Unit,
    navigateToNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser = FirebaseAuth.getInstance().currentUser


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(Blue500)
            .padding(bottom = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(0.7f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_white), // Ganti Jadi Logo
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.Top)
                    .height(44.dp)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.welcome_homepage, "Datang", "Budi"), // TODO
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(0.3f)
        ) {
            IconButton(onClick = navigateToNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = modifier.size(24.dp)
                )
            }

            IconButton(onClick = navigateToProfile) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ChildListSection(
    homePageScreenNavigator: HomePageScreenNavigator,
    data: List<ChildItem>,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Blue100)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Profil Anak",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
                    .align(Alignment.CenterVertically)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.horizontalScroll(rememberScrollState())
        ) {
            data.forEach { child ->
                val gender = if (child.gender == "Laki-laki") "Boy" else "Girl"
                var statusStunting by remember {
                    mutableStateOf("")
                }

                LaunchedEffect(key1 = child) {
                    val response = viewModel.getStatusChildren(
                        dateToDay(child.birthDay),
                        gender,
                        child.weight,
                        child.height
                    )
                    statusStunting = response.stunting.message
                }

                CardChild(children = child, status = statusStunting)
            }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
                    .padding(8.dp, 16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .height(height = 150.dp)
                    .clickable { homePageScreenNavigator.navigateToAddChildren() }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tambahkan Profil Anak",
                        color = LocalContentColor.current,
                        fontWeight = FontWeight.Light,
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.primary, // TODO
                        contentDescription = null,
                        modifier = modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.Blue)
                    )
                }
            }

        }
    }

}


@Composable
fun MenuItemSection(
    navigateToMenu: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            dummyMenu.forEach { menu ->
                MenuItem(
                    menu = menu,
                    modifier = modifier.clickable { navigateToMenu(menu.imageMenu) }
                )
            }
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
                    modifier = modifier.clickable {
                        homePageScreenNavigator.navigateToDetailPostPage(
                            it.image.toLong()
                        )
                    })  // Tambahakan Parameter Id nya
            }
        }
    }
}
