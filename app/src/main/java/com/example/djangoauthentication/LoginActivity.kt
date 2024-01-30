package com.example.djangoauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.loginButton)
        val emailET = findViewById<EditText>(R.id.loginEmailEditText)
        val passwordET = findViewById<EditText>(R.id.loginPasswordEditText)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.99:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create(AuthService::class.java)
        val authRepository = AuthRepository(authService)

        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(application, authRepository)
        )[AuthViewModel::class.java]

        loginBtn.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString() 
            authViewModel.loginUser(email, password)
        }
    }
}