package com.bangkit.stuntcare.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.stuntcare.StuntCareAppViewModel
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.di.Injection
import com.bangkit.stuntcare.ui.view.parent.children.add.AddChildrenViewModel
import com.bangkit.stuntcare.ui.view.parent.children.food_classification.FoodClassificationViewModel
import com.bangkit.stuntcare.ui.view.parent.children.history.GrowthHistoryChildrenViewModel
import com.bangkit.stuntcare.ui.view.parent.children.main.ChildrenViewModel
import com.bangkit.stuntcare.ui.view.parent.children.profile.ChildrenProfileViewModel
import com.bangkit.stuntcare.ui.view.parent.children.update.UpdateChildrenViewModel
import com.bangkit.stuntcare.ui.view.parent.consultation.detail.DetailDoctorViewModel
import com.bangkit.stuntcare.ui.view.parent.consultation.main.ConsultationViewModel
import com.bangkit.stuntcare.ui.view.parent.consultation.schedule.SetScheduleViewModel
import com.bangkit.stuntcare.ui.view.parent.home.HomeViewModel
import com.bangkit.stuntcare.ui.view.parent.login.LoginViewModel
import com.bangkit.stuntcare.ui.view.parent.register.RegisterViewModel

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

            modelClass.isAssignableFrom(GrowthHistoryChildrenViewModel::class.java) -> {
                GrowthHistoryChildrenViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ChildrenProfileViewModel::class.java) -> {
                ChildrenProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FoodClassificationViewModel::class.java) -> {
                FoodClassificationViewModel(repository) as T
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