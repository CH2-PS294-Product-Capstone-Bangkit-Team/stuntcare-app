package com.bangkit.stuntcare.ui.view.parent.consultation.schedule

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.Doctor
import com.bangkit.stuntcare.ui.model.children.Children
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SetScheduleViewModel(private val repository: DataRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Children>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Children>>>
        get() = _uiState

    fun getAllChildren(){
        viewModelScope.launch {
            repository.getAllChildren()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}