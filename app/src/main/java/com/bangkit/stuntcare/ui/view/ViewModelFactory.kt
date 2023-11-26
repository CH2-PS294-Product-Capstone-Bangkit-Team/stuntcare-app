package com.bangkit.stuntcare.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.ui.view.children.main.ChildrenViewModel
import com.bangkit.stuntcare.ui.view.children.update.UpdateChildrenViewModel
import com.bangkit.stuntcare.ui.view.consultation.detail.DetailDoctorViewModel
import com.bangkit.stuntcare.ui.view.consultation.main.ConsultationViewModel
import com.bangkit.stuntcare.ui.view.home.HomeViewModel

class ViewModelFactory(private val repository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChildrenViewModel::class.java) -> {
                ChildrenViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(UpdateChildrenViewModel::class.java) -> {
                UpdateChildrenViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ConsultationViewModel::class.java) -> {
                ConsultationViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailDoctorViewModel::class.java) -> {
                DetailDoctorViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: ViewModelFactory? = null
//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
//    }
}