package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bangkit.stuntcare.ui.navigation.Screen

class HomePageScreenNavigator(private val navController: NavController) {
    fun navigateToNotificationPage() {
        navController.navigate(Screen.Notification.route)
    }

    fun navigateToProfilePage() {
        navController.navigate(Screen.Profile.route)
    }

    fun navigateToChildPage() {
        navController.navigate(Screen.Children.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToChildPageWithId(childrenId: String){
        navController.navigate(Screen.ChildrenWithId.createRoute(childrenId)){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMenu(menuId: Int) {
        navController.navigate(Screen.Menu.createRoute(menuId))
    }

    fun navigateToArticlePage() {
        navController.navigate(Screen.Article.route){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToDetailArticle(articleId: Long) {
        navController.navigate(Screen.DetailArticle.createRoute(articleId))
    }

    fun navigateToPostPage() {
        navController.navigate(Screen.Post.route){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToDetailPostPage(postId: Long) {
        navController.navigate(Screen.DetailPost.createRoute(postId))
    }

    fun navigateToProfile(){
        navController.navigate(Screen.Profile.route)
    }

    fun backNavigation(){
        navController.navigateUp()
    }

    fun navigateToAddChildren(){
        navController.navigate(Screen.AddChildren.route)
    }
}

