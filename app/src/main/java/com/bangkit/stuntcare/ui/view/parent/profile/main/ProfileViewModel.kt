package com.bangkit.stuntcare.ui.view.parent.profile.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: DataRepository): ViewModel() {
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}