package com.bangkit.stuntcare.ui.navigation

sealed class Screen (val route: String){

    // Bottom Bar
    object HomePage: Screen("home")

    object Children: Screen("children")

    object ChildrenWithId: Screen("children/{childrenId}"){
        fun createRoute(childrenId: String) = "children/$childrenId"
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
    object UpdateChildren: Screen("children/{childrenId}"){
        fun createRoute(childrenId: String) = "children/$childrenId"
    }

    object AddChildren: Screen("children/add")

    object HistoryGrowthChildren: Screen("children/{childrenId}/history"){
        fun createRoute(childrenId: String) = "children/$childrenId/history"
    }

    object ProfileChildren: Screen("children/{childrenId}/profile"){
        fun createRoute(childrenId: String) = "children/$childrenId/profile"
    }

    // Consultation Route
    object DetailDoctor: Screen("consultation/{doctorId}"){
        fun createRoute(doctorId: Int) = "consultation/$doctorId"
    }

    object SetSchedule: Screen("consultation/{doctorId}/setSchedule"){
        fun createRoute(doctorId: Int) = "consultation/$doctorId/setSchedule"
    }

    object Chat: Screen("consultation/chat")


    // Community Route
    object Article: Screen("articles")

    object Post: Screen("posts")

    // Login
    object Login: Screen("login")

    // Register
    object Register: Screen("register")


    // Model Machine Learning
    object FoodClassification: Screen("food_classification/{childrenId}/{schedule}"){
        fun createRoute(childrenId: String, schedule: String) = "food_classification/$childrenId/$schedule"
    }

    object HeightMeasurement: Screen("height_measurement")

    object WelcomePage: Screen("welcome_page")
}