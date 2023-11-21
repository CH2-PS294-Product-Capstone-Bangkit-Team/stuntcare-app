package com.bangkit.stuntcare

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.stuntcare.ui.navigation.NavigationItem
import com.bangkit.stuntcare.ui.navigation.Screen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bangkit.stuntcare.ui.view.HomePageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StuntCareApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomePage.route,
            modifier = modifier.padding(it)
        ) {
            // Home Page Route
            composable(Screen.HomePage.route) {
                HomePageScreen(
                    navigateToNotificationPage = {
                        navController.navigate(Screen.Notification.route)
                    },
                    navigateToProfilePage = {
                        navController.navigate(Screen.Profile.route)
                    },
                    navigateToChildPage = {
                        navController.navigate(Screen.Children.createRoute(it))
                    },
                    navigateToMenu = {
                        navController.navigate(Screen.Menu.createRoute(it))
                    },
                    navigateToArticlePage = {
                        navController.navigate(Screen.Article.route)
                    },
                    navigateToDetailArticle = {
                        navController.navigate(Screen.DetailArticle.createRoute(it))
                    },
                    navigateToPostPage = {
                        navController.navigate(Screen.Post.route)
                    },
                    navigateToDetailPostPage = {
                        navController.navigate(Screen.DetailPost.createRoute(it))
                    }
                )

            }

            composable(Screen.Notification.route) {

            }

            composable(Screen.Profile.route) {

            }

            composable(
                route = Screen.Menu.route,
                arguments = listOf(navArgument("menuId") { type = NavType.IntType })
            ){
                val id = it.arguments?.getInt("menuId") ?: -1
            }


            // Children Page Route
            composable(
                route = Screen.Children.route,
                arguments = listOf(navArgument("childrenId") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("childrenId") ?: -1L
            }


            // Consultation Page Route
            composable(Screen.Consultation.route) {

            }


            // Community Page Route
            composable(Screen.Community.route) {

            }

            composable(Screen.Article.route){

            }

            composable(
                route = Screen.DetailArticle.route,
                arguments = listOf(navArgument("articleId"){type = NavType.LongType})
            ){

            }

            composable(Screen.Post.route){

            }

            composable(
                route = Screen.DetailPost.route,
                arguments = listOf(navArgument("articleId"){type = NavType.LongType})
            ){

            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.HomePage
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_children),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Children
            ),
            NavigationItem(
                title = stringResource(R.string.menu_consultation),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Consultation
            ),
            NavigationItem(
                title = stringResource(R.string.menu_community),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Community
            )
        )

        navigationItem.map {
            NavigationBarItem(
                selected = currentRoute == it.screen.route,
                onClick = {},
                icon = { Icon(painter = it.icon, contentDescription = null) },
                label = { Text(text = it.title) }
            )
        }
    }
}