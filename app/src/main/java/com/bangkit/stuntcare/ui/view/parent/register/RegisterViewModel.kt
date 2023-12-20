package com.bangkit.stuntcare.ui.view.parent.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.User
import com.google.protobuf.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: DataRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ApiResponse2>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ApiResponse2>>
        get() = _uiState


    fun registerUser(user: User) {
        viewModelScope.launch {
            repository.register(user)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}