package com.bangkit.stuntcare.ui.view.parent.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.data.remote.response.UserResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ChildItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChildItem>>>
        get() = _uiState

    fun getAllChildren() {
        viewModelScope.launch {
            repository.getAllChildrenNew()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it.data.child)
                }
        }
    }

    suspend fun getStatusChildren(
        age: Int,
        gender: String,
        weight: Float,
        height: Float
    ): ChildrenStatusResponse {
          return  repository.statusChildren(age, gender, weight, height)
    }
}