package com.example.djangoauthentication

import retrofit2.Response

class AuthRepository(private val authService: AuthService) {

    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Response<Void> {
        val request = RegistrationRequest(email, password, firstName, lastName)
        return authService.registerUser(request)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return authService.loginUser(request)
    }
}
