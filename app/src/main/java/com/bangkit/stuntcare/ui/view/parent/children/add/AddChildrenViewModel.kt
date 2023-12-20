package com.bangkit.stuntcare.ui.view.parent.children.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddChildrenViewModel(private val repository: DataRepository) : ViewModel() {


    private val _uiState: MutableStateFlow<UiState<ApiResponse2>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ApiResponse2>>
        get() = _uiState

    suspend fun addChildren(
        childrenImage: MultipartBody.Part,
        name: RequestBody,
        gender: RequestBody,
        birthDate: RequestBody,
        weight: RequestBody,
        height: RequestBody
    ): ApiResponse2 {
        return repository.addChildren(childrenImage, name, gender, birthDate, height, weight)
    }

}