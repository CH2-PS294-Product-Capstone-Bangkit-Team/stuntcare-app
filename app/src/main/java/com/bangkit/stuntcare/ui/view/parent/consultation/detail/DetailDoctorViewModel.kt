package com.bangkit.stuntcare.ui.view.parent.consultation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.DetailDoctorResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.Doctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailDoctorViewModel(val repository: DataRepository): ViewModel() {
    private val _doctor: MutableStateFlow<UiState<DetailDoctorResponse>> = MutableStateFlow(UiState.Loading)
    val doctor: StateFlow<UiState<DetailDoctorResponse>>
        get() = _doctor

    fun getDoctorById(doctorId: String){
        viewModelScope.launch {
            repository.getDoctorById(doctorId)
                .catch {
                    _doctor.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _doctor.value = UiState.Success(it)
                }
        }
    }
}