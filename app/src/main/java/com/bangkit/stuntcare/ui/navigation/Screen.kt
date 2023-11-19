package com.bangkit.stuntcare.ui.navigation

sealed class Screen (val route: String){

    // Bottom Bar
    object HomePage: Screen("home")

    object Children: Screen("children")

    object Consultation: Screen("consultation")

    object Community: Screen("community")

    // Home Page Route

    // Children Route

    // Consultation Route

    // Community Route
}