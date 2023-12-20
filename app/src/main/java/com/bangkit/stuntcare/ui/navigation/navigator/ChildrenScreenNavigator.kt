package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ChildrenScreenNavigator (val navController: NavController){
    fun navigateToUpdateData(id: String){
        navController.navigate(Screen.UpdateChildren.createRoute(id))
    }

    fun navigateToAddChildren(){
        navController.navigate(Screen.AddChildren.route)
    }

    fun navigateToGrowthHistoryChildren(id: String){
        navController.navigate(Screen.HistoryGrowthChildren.createRoute(id))
    }

    fun navigateToEditProfileChildren(id: String){
        navController.navigate(Screen.ProfileChildren.createRoute(id))
    }

    fun navigateToFoodClassification(){
        navController.navigate(Screen.FoodClassification.route)
    }
    fun backNavigation(){
        navController.navigateUp()
    }
}