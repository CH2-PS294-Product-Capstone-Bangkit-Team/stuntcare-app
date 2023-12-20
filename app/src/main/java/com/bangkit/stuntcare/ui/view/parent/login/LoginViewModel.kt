package com.bangkit.stuntcare.ui.view.parent.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserModel
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.utils.LoadingState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }

    val loadingState = MutableStateFlow(LoadingState.IDLE)


    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return repository.login(email, password)
    }
}