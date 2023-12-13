package com.bangkit.stuntcare.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.stuntcare.StuntCareAppViewModel
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.view.children.add.AddChildrenViewModel
import com.bangkit.stuntcare.ui.view.children.main.ChildrenViewModel
import com.bangkit.stuntcare.ui.view.children.update.UpdateChildrenViewModel
import com.bangkit.stuntcare.ui.view.consultation.detail.DetailDoctorViewModel
import com.bangkit.stuntcare.ui.view.consultation.main.ConsultationViewModel
import com.bangkit.stuntcare.ui.view.consultation.schedule.SetScheduleViewModel
import com.bangkit.stuntcare.ui.view.home.HomeViewModel
import com.bangkit.stuntcare.ui.view.login.LoginViewModel
import com.bangkit.stuntcare.ui.view.register.RegisterViewModel

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

            modelClass.isAssignableFrom(SetScheduleViewModel::class.java) -> {
                SetScheduleViewModel(repository) as T
            }

            modelClass.isAssignableFrom(StuntCareAppViewModel::class.java) -> {
                StuntCareAppViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AddChildrenViewModel::class.java) -> {
                AddChildrenViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}