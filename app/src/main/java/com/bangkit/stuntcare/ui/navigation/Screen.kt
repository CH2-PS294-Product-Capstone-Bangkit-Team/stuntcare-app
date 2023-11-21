package com.bangkit.stuntcare.ui.navigation

sealed class Screen (val route: String){

    // Bottom Bar
    object HomePage: Screen("home")

    object Children: Screen("children/{childrenId}"){
        fun createRoute(childrenId: Int) = "children/$childrenId"
    }

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

    // Consultation Route

    // Community Route
    object Article: Screen("articles")

    object Post: Screen("posts")
}