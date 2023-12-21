package com.bangkit.stuntcare.ui.view.parent.children.high_measurement

import androidx.lifecycle.ViewModel
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.HighMeasurementPrediction
import okhttp3.MultipartBody

class HighMeasurementPredictionViewModel(private val repository: DataRepository): ViewModel() {

    suspend fun getHeightMeasurementPrediction(image: MultipartBody.Part): HighMeasurementPrediction {
        return repository.getHeightMeasurementPrediction(image)
    }
}