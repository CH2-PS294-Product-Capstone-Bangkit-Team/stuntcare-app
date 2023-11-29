package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ConsultationScreenNavigator(private val navController: NavController) {
    fun navigateToDetailDoctor(doctorId: Int){
        navController.navigate(Screen.DetailDoctor.createRoute(doctorId))
    }

    fun navigateToSetSchedule(doctorId: Int){
        navController.navigate(Screen.SetSchedule.createRoute(doctorId))
    }

    fun backNavigation(){
        navController.navigateUp()
    }
}