package com.bangkit.stuntcare.ui.navigation

sealed class Screen (val route: String){

    // Bottom Bar
    object HomePage: Screen("home")

    object Children: Screen("children")

    object Consultation: Screen("consultation")

    object Community: Screen("community")

    // Home Page Route
    // 1. Notification
    object Notification: Screen("notification")

    // 2. Profile
    object  Profile: Screen("Profile")

    // 3. Menu
    object Menu: Screen("menu/{menuId}"){
        fun createRoute(menuId:Int) = "menu/$menuId"
    }

    // 4. Detail Article
    object DetailArticle: Screen("articles/{articleId}"){
        fun createRoute(articleId: Long) = "articles/$articleId"
    }

    // 5. Detail Post
    object DetailPost: Screen("posts/{postId}"){
        fun createRoute(postId: Long) = "posts/$postId"
    }

    // Children Route
    object UpdateChildren: Screen("children/{childrenId}"){
        fun createRoute(childrenId: Int) = "children/$childrenId"
    }

    // Consultation Route
    object DetailDoctor: Screen("consultation/{doctorId}"){
        fun createRoute(doctorId: Int) = "consultation/$doctorId"
    }

    object SetSchedule: Screen("consultation/{doctorId}/setSchedule"){
        fun createRoute(doctorId: Int) = "consultation/$doctorId/setSchedule"
    }



    // Community Route
    object Article: Screen("articles")

    object Post: Screen("posts")
}