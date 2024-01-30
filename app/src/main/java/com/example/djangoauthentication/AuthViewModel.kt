package com.example.djangoauthentication

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(application: Application, private val authRepository: AuthRepository) : AndroidViewModel(application) {

    val firstName: MutableLiveData<String> = MutableLiveData()
    val accessToken: MutableLiveData<String> = MutableLiveData()
    private val registrationResult: MutableLiveData<Boolean> = MutableLiveData()
    private val loginResult: MutableLiveData<Boolean> = MutableLiveData()

    fun registerUser(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(email, password, firstName, lastName)

                if (response.isSuccessful) {
                    showToast("Registration successful")
                    registrationResult.value = true
                } else {
                    showToast("Registration failed")
                    registrationResult.value = false
                }

            } catch (e: Exception) {
                Log.e("Error during registration", "Error: $e")
                registrationResult.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(email, password)

                if (response.isSuccessful) {
                    Log.d("Login successful","Response: ${response.body()}")
                    response.body()?.let {
                        accessToken.value = it.access
                        firstName.value = it.user.first_name
                        Log.d("AuthViewModel", "Access Token: ${it.access}")
                    }
                    loginResult.value = true
                } else {
                    showToast("Login failed: ${response.code()} - ${response.message()}")
                    loginResult.value = false
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error during login", e)
                showToast("Error during login")
                loginResult.value = false
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }
}


