package com.dicoding.seitest.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.seitest.API.UserRepository
import com.dicoding.seitest.API.response.RegisterResponse
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class RegisterViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val registerResponse = MutableLiveData<RegisterResponse>()
    val isRegistrationSuccessful = MutableLiveData<Boolean>()

    fun register(raw: RequestBody) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response: RegisterResponse = repository.register(raw)
                registerResponse.value = response
                isRegistrationSuccessful.value = true
            } catch (e: Exception) {
                // Handle the error here
                isRegistrationSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}
