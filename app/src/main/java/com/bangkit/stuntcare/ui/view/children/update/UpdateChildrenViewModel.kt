package com.bangkit.stuntcare.ui.view.children.update

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.common.UiState
import com.bangkit.stuntcare.ui.model.children.Children
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateChildrenViewModel(private val repository: DataRepository): ViewModel() {
    private val _children: MutableStateFlow<Children?> = MutableStateFlow(null)
    val children: StateFlow<Children?>
        get() = _children

    fun getChildrenById(id: Int){
        viewModelScope.launch {
            _children.value = repository.getChildrenById(id)
        }
    }
}