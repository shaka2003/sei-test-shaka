package com.dicoding.seitest.listUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.seitest.API.response.GetUserResponse
import com.dicoding.seitest.API.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val userResponse: MutableLiveData<GetUserResponse> = MutableLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUsers() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getUsers().enqueue(object : Callback<GetUserResponse> {
            override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    userResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                _isLoading.value = false
                // Handle the error here
            }
        })
    }
}



