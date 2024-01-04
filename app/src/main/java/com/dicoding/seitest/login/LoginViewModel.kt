package com.dicoding.seitest.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.seitest.API.UserRepository
import com.dicoding.seitest.API.response.LoginResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        val json = """
            {
                "username": "$username",
                "password": "$password"
            }
        """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = userRepository.Login(requestBody)
                // If the login was successful, update _loginResult to notify the activity
                _loginResult.value = response
            } catch (e: Exception) {
                // Handle any exceptions that occurred during the login operation.
            } finally {
                _isLoading.value = false
            }
        }
    }
}

