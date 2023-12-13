package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ProfileScreenNavigator(private val navController: NavController) {
    fun logOut(){
        navController.navigate(Screen.Login.route)
        navController.clearBackStack(Screen.Login.route)
    }

    fun backNavigation(){
        navController.navigateUp()
    }
}