package com.dicoding.seitest.API.service

import com.dicoding.seitest.API.response.GetUserResponse
import com.dicoding.seitest.API.response.LoginResponse
import com.dicoding.seitest.API.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(
        @Body raw: RequestBody
    ): LoginResponse

    @POST("api/register")
    suspend fun register(
        @Body raw: RequestBody
    ): RegisterResponse

    @GET("api/getUsers")
    fun getUsers(): Call<GetUserResponse>
}