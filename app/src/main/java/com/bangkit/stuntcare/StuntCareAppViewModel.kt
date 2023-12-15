package com.bangkit.stuntcare

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StuntCareAppViewModel (private val repository: DataRepository): ViewModel(){

    private val _thisSession: MutableStateFlow<UserModel> = MutableStateFlow(UserModel("", "", "", false))
    val thisSession: StateFlow<UserModel>
        get() = _thisSession

    private fun getSession() {
        viewModelScope.launch {
            repository.getSession()
                .catch {

                }
                .collect {
                    _thisSession.value = it
                }
        }
    }

    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}