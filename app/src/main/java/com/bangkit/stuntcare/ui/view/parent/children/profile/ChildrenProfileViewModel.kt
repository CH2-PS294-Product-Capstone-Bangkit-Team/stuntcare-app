package com.bangkit.stuntcare.ui.view.parent.children.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ChildrenProfileViewModel(private val repository: DataRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DetailChildrenResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailChildrenResponse>>
        get() = _uiState

    fun getChildrenById(childrenId: String){
        viewModelScope.launch {
            repository.getChildrenByIdApi(childrenId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    suspend fun updateProfileChildren(
        childrenId: String,
        image: MultipartBody.Part,
        name: RequestBody,
        weight: RequestBody,
        height: RequestBody
    ): ApiResponse2{
        return repository.updateChildren(childrenId, image, name, weight, height)
    }
    suspend fun updateProfileChildren(
        childrenId: String,
        name: RequestBody,
        weight: RequestBody,
        height: RequestBody
    ): ApiResponse2{
        return repository.updateChildren(childrenId, name, weight, height)
    }
}