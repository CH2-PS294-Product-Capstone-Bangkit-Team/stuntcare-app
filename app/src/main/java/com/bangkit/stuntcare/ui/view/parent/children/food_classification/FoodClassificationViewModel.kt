package com.bangkit.stuntcare.ui.view.parent.children.food_classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.FoodClassificationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class FoodClassificationViewModel(private val repository: DataRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<HighMeasurementPrediction>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<HighMeasurementPrediction>>
        get() = _uiState


    fun getFoodClassification(image: MultipartBody.Part){
        viewModelScope.launch {
            repository.getFoodClassification(image)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}