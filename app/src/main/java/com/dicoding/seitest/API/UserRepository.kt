package com.dicoding.seitest.API

import com.dicoding.seitest.API.response.LoginResponse
import com.dicoding.seitest.API.response.RegisterResponse
import com.dicoding.seitest.API.service.ApiService
import okhttp3.RequestBody

class UserRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun register(raw: RequestBody): RegisterResponse {
        return apiService.register(raw)
    }

    suspend fun Login(raw: RequestBody): LoginResponse {
        return apiService.login(raw)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }

        fun resetInstance() {
            instance = null
        }
    }
}