package com.bangkit.stuntcare.ui.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.utils.LoadingState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val repository: DataRepository) : ViewModel() {
    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun signInWithGoogleCredential(credential: AuthCredential){
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                Firebase.auth.signInWithCredential(credential).await()
                loadingState.emit(LoadingState.LOADED)
            }catch (e: Exception){
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }
    }
}