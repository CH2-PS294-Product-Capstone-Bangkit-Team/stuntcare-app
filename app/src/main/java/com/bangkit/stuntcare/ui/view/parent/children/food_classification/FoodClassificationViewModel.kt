package com.bangkit.stuntcare.ui.view.parent.children.food_classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.FoodClassificationResponse
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import com.bangkit.stuntcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FoodClassificationViewModel(private val repository: DataRepository) : ViewModel() {
    suspend fun getFoodClassification(image: MultipartBody.Part): FoodClassificationResponse {
        return repository.getFoodClassification(image)
    }

    suspend fun addFoodChildren(
        childrenId: String,
        image: MultipartBody.Part,
        foodName: RequestBody,
        schedule: RequestBody
    ): ApiResponse2 {
        return repository.addChildrenFood(childrenId, image, foodName, schedule)
    }
}