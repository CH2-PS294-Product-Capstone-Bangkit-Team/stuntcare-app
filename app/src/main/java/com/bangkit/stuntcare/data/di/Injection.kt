package com.bangkit.stuntcare.data.di

import android.content.Context
import com.bangkit.stuntcare.data.DataRepository
import com.bangkit.stuntcare.data.pref.UserPreference
import com.bangkit.stuntcare.data.pref.dataStore
import com.bangkit.stuntcare.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val baseUrlFromCc = "https://stuntcare-api-nts3atbn5q-et.a.run.app/"
        val baseUrlGetStatus = "https://stunting-status-nts3atbn5q-de.a.run.app/"
        val baseUrlFoodClassification = "https://food-classification-nts3atbn5q-de.a.run.app/"
        val baseUrlHighMeasurement = "https://height-prediction-nts3atbn5q-de.a.run.app/"
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiServiceFromCc = ApiConfig.getApiService(user.token, baseUrlFromCc)
        val apiServiceGetStatus = ApiConfig.getApiService(user.token, baseUrlGetStatus)
        val apiServiceFoodClassification =
            ApiConfig.getApiService(user.token, baseUrlFoodClassification)
        val apiServiceHighMeasurement = ApiConfig.getApiService(user.token, baseUrlHighMeasurement)

        return DataRepository.getInstance(
            userPreference = pref,
            apiServiceFromCc = apiServiceFromCc,
            apiServiceGetStatus = apiServiceGetStatus,
            apiServiceFoodClassification = apiServiceFoodClassification,
            apiServiceHighMeasurement = apiServiceHighMeasurement
        )
    }
}