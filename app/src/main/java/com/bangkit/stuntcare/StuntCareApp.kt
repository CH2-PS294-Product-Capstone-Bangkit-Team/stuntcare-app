package com.bangkit.stuntcare

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.stuntcare.ui.navigation.NavigationItem
import com.bangkit.stuntcare.ui.navigation.Screen
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        NavHost(navController = navController, startDestination = Screen.HomePage.route, modifier = modifier.padding(it)) {
            composable(Screen.HomePage.route){
                HomePageScreen()
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