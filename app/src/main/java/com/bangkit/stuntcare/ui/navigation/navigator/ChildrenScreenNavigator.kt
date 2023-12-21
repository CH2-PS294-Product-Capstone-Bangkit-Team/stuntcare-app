package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.lifecycle.MutableLiveData
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

    fun navigateToHeightMeasurement(){
        navController.navigate(Screen.HeightMeasurement.route)
    }
    fun backNavigation(){
        navController.navigateUp()
    }

    fun getResult(): Float?{
        return navController.currentBackStackEntry?.savedStateHandle?.get<Float>("height")
    }

    fun backNavigationWithHeight(height: Float?){
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("height", height)
        navController.popBackStack()
    }
}