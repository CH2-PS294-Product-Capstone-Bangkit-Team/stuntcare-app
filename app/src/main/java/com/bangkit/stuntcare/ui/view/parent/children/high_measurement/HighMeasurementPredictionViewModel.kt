package com.bangkit.stuntcare.ui.view.parent.children.high_measurement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HighMeasurementPredictionViewModel(private val repository: DataRepository): ViewModel() {
    suspend fun getHeightMeasurementPrediction(image: MultipartBody.Part): HighMeasurementPrediction {
            return repository.getHeightMeasurementPrediction(image)
    }
}