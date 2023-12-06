package com.bangkit.stuntcare

import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.stuntcare.ui.navigation.NavigationItem
import com.bangkit.stuntcare.ui.navigation.Screen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bangkit.stuntcare.ui.navigation.navigator.ChildrenScreenNavigator
import com.bangkit.stuntcare.ui.navigation.navigator.CommunityScreenNavigator
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator
import com.bangkit.stuntcare.ui.view.children.add.AddChildrenScreen
import com.bangkit.stuntcare.ui.view.children.main.ChildrenScreen
import com.bangkit.stuntcare.ui.view.children.update.UpdateChildrenScreen
import com.bangkit.stuntcare.ui.view.community.CommunityScreen
import com.bangkit.stuntcare.ui.view.consultation.chat.ChatScreen
import com.bangkit.stuntcare.ui.view.consultation.detail.DetailDoctorScreen
import com.bangkit.stuntcare.ui.view.consultation.main.ConsultationScreen
import com.bangkit.stuntcare.ui.view.consultation.schedule.SetScheduleScreen
import com.bangkit.stuntcare.ui.view.home.HomePageScreen
import com.bangkit.stuntcare.ui.view.login.LoginScreen
import com.bangkit.stuntcare.ui.view.profile.main.ProfileScreen

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
            when (currentRoute) {
                Screen.HomePage.route -> BottomBar(navController = navController)
                Screen.Children.route -> BottomBar(navController = navController)
                Screen.Consultation.route -> BottomBar(navController = navController)
                Screen.Community.route -> BottomBar(navController = navController)
                else -> {}
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomePage.route,
            modifier = modifier.padding(it)
        ) {
            // Home Page Route
            composable(Screen.HomePage.route) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        if (it.resultCode == RESULT_OK){
                        }
                    }
                )
                HomePageScreen(
                    homePageScreenNavigator = HomePageScreenNavigator(navController)
                )

            }

            composable(Screen.Notification.route) {
                // TODO: Tambahkan Screen
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navigator = HomePageScreenNavigator(navController = navController))
            }

            composable(
                route = Screen.Menu.route,
                arguments = listOf(navArgument("menuId") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("menuId") ?: -1
            }


            // Children Page Route
            composable(
                route = Screen.Children.route
            ) {
                ChildrenScreen(navigator = ChildrenScreenNavigator(navController = navController))
            }

            composable(route = Screen.AddChildren.route){
                AddChildrenScreen(navigator = ChildrenScreenNavigator(navController))
            }


            // Consultation Page Route
            composable(Screen.Consultation.route) {
                ConsultationScreen(navigator = ConsultationScreenNavigator(navController))
            }

            composable(
                route = Screen.DetailDoctor.route,
                arguments = listOf(navArgument("doctorId"){type = NavType.IntType})
            ){
                val doctorId = it.arguments?.getInt("doctorId") ?: 0
                DetailDoctorScreen(doctorId = doctorId, navigator = ConsultationScreenNavigator(navController))
            }

            composable(
                route = Screen.SetSchedule.route
            ){
                val doctorId = it.arguments?.getInt("doctorId") ?: 0
                SetScheduleScreen(
                    doctorId = doctorId,
                    navigator = ConsultationScreenNavigator(navController)
                )
            }

            composable(Screen.Chat.route){
                ChatScreen(navigator = ConsultationScreenNavigator(navController = navController))
            }


            // Community Page Route
            composable(Screen.Community.route) {
                CommunityScreen(navigator = CommunityScreenNavigator(navController = navController))
            }

            composable(Screen.Article.route) {
                // TODO: Tambahkan Screen
            }

            composable(
                route = Screen.DetailArticle.route,
                arguments = listOf(navArgument("articleId") { type = NavType.LongType })
            ) {
                // TODO: Tambahkan Screen
            }

            composable(Screen.Post.route) {
                // TODO: Tambahkan Screen
            }

            composable(
                route = Screen.DetailPost.route,
                arguments = listOf(navArgument("articleId") { type = NavType.LongType })
            ) {
                // TODO: Tambahkan Screen
            }

            composable(
                route = Screen.UpdateChildren.route,
                arguments = listOf(navArgument("childrenId") { type = NavType.IntType })
            ) {
                val childrenId = it.arguments?.getInt("childrenId") ?: 0
                UpdateChildrenScreen(
                    childrenId = childrenId,
                    navigator = ChildrenScreenNavigator(navController = navController)
                )
            }

            // Login
            composable(Screen.Login.route){
                LoginScreen( {} )
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
        modifier = modifier.alpha(1f)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.HomePage,
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_children),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Children,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_consultation),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Consultation,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_community),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Community,
            )
        )

        navigationItem.map {
            NavigationBarItem(
                selected = currentRoute == it.screen.route,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painter = it.icon, contentDescription = null) },
                label = {
                    if (currentRoute == it.screen.route) {
                        Text(text = it.title)
                    }
                }
            )
        }
    }
}