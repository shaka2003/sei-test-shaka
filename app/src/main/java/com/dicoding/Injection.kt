package com.dicoding

import android.content.Context
import com.dicoding.seitest.API.UserRepository
import com.dicoding.seitest.API.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

}