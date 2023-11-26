package com.bangkit.stuntcare.ui.view.consultation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.Doctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConsultationViewModel(private val repository: DataRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Doctor>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Doctor>>>
        get() = _uiState

    private val _listDoctor: MutableStateFlow<List<Doctor>?> = MutableStateFlow(null)
    val listDoctor: StateFlow<List<Doctor>?>
        get() = _listDoctor

    private val _query = mutableStateOf("")
    val query: State<String>
        get() = _query

    fun getAllDoctor(){
        viewModelScope.launch {
            repository.getAllDoctor()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun searchDoctor(newQuery: String){
        _query.value = newQuery
        _listDoctor.value = repository.searchDoctor(_query.value)
    }
}