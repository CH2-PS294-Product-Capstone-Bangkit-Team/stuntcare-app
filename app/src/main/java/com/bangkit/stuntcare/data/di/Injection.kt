package com.bangkit.stuntcare.data.di

import android.content.Context
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserPreference
import com.bangkit.stuntcare.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return DataRepository.getInstance(userPreference = pref)
    }
}