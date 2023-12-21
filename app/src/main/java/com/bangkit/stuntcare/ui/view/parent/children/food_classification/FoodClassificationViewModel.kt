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

class FoodClassificationViewModel(private val repository: DataRepository) : ViewModel() {
    private val _getFoodClassification: MutableStateFlow<UiState<FoodClassificationResponse>> =
        MutableStateFlow(UiState.Loading)
    val getFoodClassification: StateFlow<UiState<FoodClassificationResponse>>
        get() = _getFoodClassification


    fun getFoodClassification(image: MultipartBody.Part) {
        viewModelScope.launch {
            repository.getFoodClassification(image)
                .catch {
                    _getFoodClassification.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _getFoodClassification.value = UiState.Success(it)
                }
        }
    }
}