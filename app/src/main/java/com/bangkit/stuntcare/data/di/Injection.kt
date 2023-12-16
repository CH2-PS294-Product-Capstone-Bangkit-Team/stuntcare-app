package com.bangkit.stuntcare.data.di

import android.content.Context
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserPreference
import com.bangkit.stuntcare.data.pref.dataStore
import com.bangkit.stuntcare.data.remote.ApiConfig
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val auth = FirebaseAuth.getInstance().currentUser
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)

        return DataRepository.getInstance(userPreference = pref, apiService = apiService)
    }
}