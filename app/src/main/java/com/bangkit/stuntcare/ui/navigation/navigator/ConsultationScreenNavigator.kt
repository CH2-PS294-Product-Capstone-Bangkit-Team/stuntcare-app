package com.bangkit.stuntcare.ui.navigation.navigator

import androidx.navigation.NavController
import com.bangkit.stuntcare.ui.navigation.Screen

class ConsultationScreenNavigator(private val navController: NavController) {
    fun navigateToDetailDoctor(doctorId: String){
        navController.navigate(Screen.DetailDoctor.createRoute(doctorId))
    }

    fun navigateToSetSchedule(doctorId: String){
        navController.navigate(Screen.SetSchedule.createRoute(doctorId))
    }

    fun navigateToChatScreen(){
        navController.navigate(Screen.Chat.route)
    }

    fun navigateToRoomChatScreen(){
        navController.navigate(Screen.RoomChat.route)
    }

    fun backNavigation(){
        navController.navigateUp()
    }
}