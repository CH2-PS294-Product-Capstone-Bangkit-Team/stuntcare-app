package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ChildrenScreenNavigator (val navController: NavController){
    fun navigateToUpdateData(id: Int){
        navController.navigate(Screen.UpdateChildren.createRoute(id))
    }

    fun backNavigation(){
        navController.navigateUp()
    }
}