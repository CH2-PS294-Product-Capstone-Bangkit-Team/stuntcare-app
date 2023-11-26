package com.bangkit.stuntcare.ui.view.children.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChildrenViewModel(val repository: DataRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Children>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Children>>>
        get() = _uiState

    private val _childrenById: MutableStateFlow<Children?> = MutableStateFlow(null)
    val children: StateFlow<Children?>
        get() = _childrenById

    private val _firstChildren: MutableStateFlow<Children?> = MutableStateFlow(null)
    init {
        getFirstChildren()
        getChildrenById(_firstChildren.value!!.id)
    }
    fun getChildrenById(id: Int) {
        viewModelScope.launch {
            val child = repository.getChildrenById(id)
            _childrenById.value = child
        }
    }

    fun getAllChildren() {
        viewModelScope.launch {
            repository.getAllChildren()
                .catch {
                    _uiState.value = UiState.Loading
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun getFirstChildren() {
        viewModelScope.launch {
            _firstChildren.value = repository.getFirstChildren()
        }
    }

}