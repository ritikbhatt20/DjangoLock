package com.example.djangoauthentication

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(application: Application, private val authRepository: AuthRepository) : AndroidViewModel(application) {

    fun registerUser(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(email, password, firstName, lastName)

                if (response.isSuccessful) {
                    showToast("Registration successful")
                }
                else  {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegistrationError", "Error: $errorBody")
                }

            } catch (e: Exception) {
                showToast("Error during registration $e")
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(email, password)

                if (response.isSuccessful) {
                    showToast("Login successful")
                } else {
                    showToast("Login failed")
                }
            } catch (e: Exception) {
                showToast("Error during login")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }
}

