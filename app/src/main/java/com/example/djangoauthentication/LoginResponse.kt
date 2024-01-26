package com.example.djangoauthentication

data class LoginResponse(val access: String, val refresh: String, val user: UserProfile)
data class UserProfile(val id: Int, val email: String, val first_name: String, val last_name: String)
