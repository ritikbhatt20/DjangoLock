package com.example.djangoauthentication

// AuthService.kt

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class RegistrationRequest(val email: String, val password: String, val first_name: String, val last_name: String)
data class LoginRequest(val email: String, val password: String)

interface AuthService {

    @POST("api/register/")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest): Response<Void>

    @POST("api/login/")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
