package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ConsultationScreenNavigator(val navController: NavController) {
    fun navigateToDetailDoctor(doctorId: Int){
        navController.navigate(Screen.DetailDocter.createRoute(doctorId))
    }
}