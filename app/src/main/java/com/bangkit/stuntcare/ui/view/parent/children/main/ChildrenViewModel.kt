package com.bangkit.stuntcare.ui.view.parent.children.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.remote.response.ApiResponse2
import com.bangkit.stuntcare.data.remote.response.ChildItem
import com.bangkit.stuntcare.data.remote.response.ChildrenFoodResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import com.bangkit.stuntcare.data.remote.response.ChildrenStatusResponse
import com.bangkit.stuntcare.data.remote.response.DetailChildrenResponse
import com.bangkit.stuntcare.data.remote.response.FoodRecommendationResponse
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import com.bangkit.stuntcare.ui.utils.showToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ChildrenViewModel(val repository: DataRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<DetailChildrenResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailChildrenResponse>>
        get() = _uiState

    private val _allChild: MutableStateFlow<UiState<List<ChildItem>>> =
        MutableStateFlow(UiState.Loading)
    val allChild: MutableStateFlow<UiState<List<ChildItem>>>
        get() = _allChild

    init {
        getAllChildren()
    }

    fun getChildrenById(childrenId: String) {
        viewModelScope.launch {
            repository.getChildrenByIdApi(childrenId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun getAllChildren() {
        viewModelScope.launch {
            repository.getAllChildrenNew()
                .catch {
                    _allChild.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _allChild.value = UiState.Success(it.data.child)
                }
        }
    }

    suspend fun getStatusChildren(
        age: Int,
        gender: String,
        weight: Float,
        height: Float
    ): ChildrenStatusResponse {
        return repository.statusChildren(age, gender, weight, height)
    }

    suspend fun getFoodRecommendation(): FoodRecommendationResponse {
        return repository.getFoodRecommendation()
    }

    private val _childrenFood: MutableStateFlow<UiState<ChildrenFoodResponse>> =
        MutableStateFlow(UiState.Loading)
    val childrenFood: MutableStateFlow<UiState<ChildrenFoodResponse>> = _childrenFood


    suspend fun getChildrenFood(childrenId: String) {
        viewModelScope.launch {
            repository.getChildrenFood(childrenId)
                .catch {
                    _childrenFood.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _childrenFood.value = UiState.Success(it)
                }
        }
    }

    suspend fun getChildrenFood2(childrenId: String): ChildrenFoodResponse {
        return repository.getChildrenFood2(childrenId)
    }


}