package com.bangkit.stuntcare.ui.view.parent.children.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GrowthHistoryChildrenViewModel(private val repository: DataRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ChildrenResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ChildrenResponse>>
        get() = _uiState

    private val _childrenById: MutableStateFlow<UiState<DetailChildrenResponse>> =
        MutableStateFlow(UiState.Loading)
    val childrenById: StateFlow<UiState<DetailChildrenResponse>>
        get() = _childrenById

    private val _statusChildren: MutableStateFlow<UiState<ChildrenStatusResponse>> =
        MutableStateFlow(UiState.Loading)
    val statusChildren: StateFlow<UiState<ChildrenStatusResponse>>
        get() = _statusChildren


    fun getAllChildren() {
        viewModelScope.launch {
            repository.getAllChildrenNew()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun getChildrenById(childrenId: String) {
        viewModelScope.launch {
            repository.getChildrenByIdApi(childrenId)
                .catch {
                    _childrenById.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _childrenById.value = UiState.Success(it)
                }
        }
    }

    suspend fun getStatusChildren(
        age: Int,
        gender: String,
        weight: Float,
        height: Float
    ) {
        viewModelScope.launch {
            repository.statusChildren(age, gender, weight, height)
                .catch {
                    _statusChildren.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _statusChildren.value = UiState.Success(it)
                }
        }
    }
}