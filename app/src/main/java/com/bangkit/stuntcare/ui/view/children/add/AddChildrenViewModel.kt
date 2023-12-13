package com.bangkit.stuntcare.ui.view.children.add

import androidx.lifecycle.ViewModel
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddChildrenViewModel (private val repository: DataRepository): ViewModel() {
    suspend fun addChildren(childrenImage: MultipartBody.Part, name: RequestBody, birthDate: RequestBody, weight: RequestBody, height: RequestBody): ApiResponse{
        return repository.addChildren(childrenImage, name, birthDate, height, weight)
    }
}