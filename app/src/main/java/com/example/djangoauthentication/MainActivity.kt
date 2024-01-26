package com.example.djangoauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerBtn = findViewById<Button>(R.id.registerButton)

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

        registerBtn.setOnClickListener {
            authViewModel.registerUser("ritik@example.com", "password", "John", "Doe")
        }

        // Example: Login User
        authViewModel.loginUser("user@example.com", "password")
    }
}
