package com.bangkit.stuntcare.ui.view.parent.children.update

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UpdateChildrenViewModel(private val repository: DataRepository): ViewModel() {
    private val _children: MutableStateFlow<DetailChildrenResponse?> = MutableStateFlow(null)
    val children: StateFlow<DetailChildrenResponse?>
        get() = _children

    fun getChildrenById(id: String){
        viewModelScope.launch {
            repository.getChildrenByIdApi(id)
                .collect {
                    _children.value = it
                }
        }
    }


    suspend fun updateChildren(id: String, weight: Float, height: Float): ApiResponse2 {
           return repository.updateChildren(id = id, height = height, weight = weight)
    }
}