package com.bangkit.stuntcare.ui.view.parent.consultation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.model.Doctor
import kotlinx.coroutines.launch

class DetailDoctorViewModel(val repository: DataRepository): ViewModel() {
    private val _doctor: MutableState<Doctor?> = mutableStateOf(null)
    val doctor: State<Doctor?>
        get() = _doctor

    fun getDoctorById(doctorId: Int){
        viewModelScope.launch {
            _doctor.value = repository.getDoctorById(doctorId)
        }
    }
}