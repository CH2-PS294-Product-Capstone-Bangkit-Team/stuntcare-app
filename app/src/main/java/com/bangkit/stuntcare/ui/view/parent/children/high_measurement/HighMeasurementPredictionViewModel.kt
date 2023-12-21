package com.bangkit.stuntcare.ui.view.parent.children.high_measurement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HighMeasurementPredictionViewModel(private val repository: DataRepository): ViewModel() {

    private val _getHeight: MutableStateFlow<UiState<HighMeasurementPrediction>> = MutableStateFlow(UiState.Loading)
    val getHeight: StateFlow<UiState<HighMeasurementPrediction>>
        get() = _getHeight
    fun getHeightMeasurementPrediction(image: MultipartBody.Part) {
        viewModelScope.launch {
            repository.getHeightMeasurementPrediction(image)
                .catch {
                    _getHeight.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _getHeight.value = UiState.Success(it)
                }
        }
    }
}