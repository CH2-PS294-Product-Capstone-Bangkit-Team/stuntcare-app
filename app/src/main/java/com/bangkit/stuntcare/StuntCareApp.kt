package com.bangkit.stuntcare

import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
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
import com.bangkit.stuntcare.ui.navigation.navigator.ProfileScreenNavigator
import com.bangkit.stuntcare.ui.view.ViewModelFactory
import com.bangkit.stuntcare.ui.view.parent.children.add.AddChildrenScreen
import com.bangkit.stuntcare.ui.view.parent.children.food_classification.FoodClassificationScreen
import com.bangkit.stuntcare.ui.view.parent.children.high_measurement.HighMeasurementScreen
import com.bangkit.stuntcare.ui.view.parent.children.history.GrowthHistoryScreen
import com.bangkit.stuntcare.ui.view.parent.children.main.ChildrenScreen
import com.bangkit.stuntcare.ui.view.parent.children.profile.ChildrenProfileScreen
import com.bangkit.stuntcare.ui.view.parent.children.update.UpdateChildrenScreen
import com.bangkit.stuntcare.ui.view.parent.community.CommunityScreen
import com.bangkit.stuntcare.ui.view.parent.consultation.chat.ChatScreen
import com.bangkit.stuntcare.ui.view.parent.consultation.detail.DetailDoctorScreen
import com.bangkit.stuntcare.ui.view.parent.consultation.main.ConsultationScreen
import com.bangkit.stuntcare.ui.view.parent.consultation.schedule.SetScheduleScreen
import com.bangkit.stuntcare.ui.view.parent.home.HomePageScreen
import com.bangkit.stuntcare.ui.view.parent.login.LoginScreen
import com.bangkit.stuntcare.ui.view.parent.login.LoginWithGoogleScreen
import com.bangkit.stuntcare.ui.view.parent.profile.main.ProfileScreen
import com.bangkit.stuntcare.ui.view.parent.register.RegisterScreen
import com.bangkit.stuntcare.ui.view.parent.welcome.WelcomePageScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StuntCareApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: StuntCareAppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val thisSession = viewModel.thisSession.collectAsState().value

    val currentUser = FirebaseAuth.getInstance().currentUser

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
            startDestination = if (currentUser == null) {
                Screen.WelcomePage.route
            } else {
                Screen.HomePage.route
            },
            modifier = modifier.padding(it)
        ) {
            // Home Page Route
            composable(Screen.HomePage.route) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        if (it.resultCode == RESULT_OK) {
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
                ProfileScreen(navigator = ProfileScreenNavigator(navController = navController))
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
                val height = it.savedStateHandle.get<Float?>("height")
                ChildrenScreen(navigator = ChildrenScreenNavigator(navController = navController))
            }

            composable(route = Screen.AddChildren.route) {
                AddChildrenScreen(navigator = ChildrenScreenNavigator(navController))
            }

            composable(
                route = Screen.HistoryGrowthChildren.route,
                arguments = listOf(navArgument("childrenId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("childrenId")
                GrowthHistoryScreen(
                    childrenId = id,
                    navigator = ChildrenScreenNavigator(navController)
                )
            }

            composable(
                route = Screen.ProfileChildren.route,
                arguments = listOf(navArgument("childrenId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("childrenId")
                ChildrenProfileScreen(
                    childrenId = id,
                    navigator = ChildrenScreenNavigator(navController)
                )
            }


            // Consultation Page Route
            composable(Screen.Consultation.route) {
                ConsultationScreen(navigator = ConsultationScreenNavigator(navController))
            }

            composable(
                route = Screen.DetailDoctor.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
            ) {
                val doctorId = it.arguments?.getInt("doctorId") ?: 0
                DetailDoctorScreen(
                    doctorId = doctorId,
                    navigator = ConsultationScreenNavigator(navController)
                )
            }

            composable(
                route = Screen.SetSchedule.route
            ) {
                val doctorId = it.arguments?.getInt("doctorId") ?: 0
                SetScheduleScreen(
                    doctorId = doctorId,
                    navigator = ConsultationScreenNavigator(navController)
                )
            }

            composable(Screen.Chat.route) {
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
                arguments = listOf(navArgument("childrenId") { type = NavType.StringType })
            ) {
                val childrenId = it.arguments?.getString("childrenId") ?: ""
                UpdateChildrenScreen(
                    childrenId = childrenId,
                    navigator = ChildrenScreenNavigator(navController = navController)
                )
            }

            // Login
            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToHome = {
                        navController.navigate(Screen.HomePage.route)
                        navController.clearBackStack(Screen.HomePage.route)
                    },
                    navigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateToLogin = { navController.navigate(Screen.Login.route) }
                )
            }

            composable(
                route = Screen.FoodClassification.route,
                arguments = listOf(
                    navArgument("childrenId") { type = NavType.StringType },
                    navArgument("schedule") { type = NavType.StringType }
                )
            ) {
                val childrenId = it.arguments?.getString("childrenId") ?: ""
                val schedule = it.arguments?.getString("schedule") ?: ""
                FoodClassificationScreen(childrenId, schedule, ChildrenScreenNavigator(navController))
            }

            composable(Screen.HeightMeasurement.route) {
                HighMeasurementScreen(
                    childrenScreenNavigator = ChildrenScreenNavigator(navController)
                )
            }

            composable(Screen.WelcomePage.route) {
                WelcomePageScreen(
                    navigateToLoginScreen = { navController.navigate(Screen.Login.route) },
                    navigateToRegisterScreen = { navController.navigate(Screen.Register.route) })
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = painterResource(id = R.drawable.bottom_bar_home),
                screen = Screen.HomePage,
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_children),
                icon = painterResource(id = R.drawable.ic_child_menu),
                screen = Screen.Children,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_consultation),
                icon = painterResource(id = R.drawable.bottom_bar_consultation),
                screen = Screen.Consultation,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_community),
                icon = painterResource(id = R.drawable.bottom_bar_community),
                screen = Screen.Community,
            )
        )
        navigationItem.map {
            NavigationBarItem(
                icon = { Icon(painter = it.icon, contentDescription = it.title) },
                label = { Text(text = it.title) },
                selected = currentRoute == it.screen.route,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun AddItem(
    screen: NavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.screen.route } == true

    val background =
        if (selected) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) MaterialTheme.colorScheme.onSecondaryContainer else LocalContentColor.current

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = screen.icon,
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor,
                    modifier = Modifier
                )
            }
        }
    }
}
