package com.bangkit.stuntcare.data.di

import com.bangkit.stuntcare.data.DataRepository

object Injection {
    fun provideRepository(): DataRepository {
        return DataRepository.getInstance()
    }
}