package com.example.djangoauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
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

        // Observe the firstName LiveData here
        authViewModel.firstName.observe(this, Observer { firstName ->
            if (!firstName.isNullOrEmpty()) {
                showWelcomeMessage(firstName)
                navigateToDashboard(firstName)
            }
        })

        // Observe the accessToken LiveData here
        authViewModel.accessToken.observe(this, Observer { accessToken ->
            if (!accessToken.isNullOrEmpty()) {
//                showAccessToken(accessToken)
            }
        })

        loginBtn.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString()

            authViewModel.loginUser(email, password)
        }
    }

    private fun showWelcomeMessage(firstName: String) {
        Toast.makeText(this, "Welcome, $firstName!", Toast.LENGTH_SHORT).show()
    }

    private fun showAccessToken(accessToken: String) {
        Toast.makeText(this, "Access Token: $accessToken", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDashboard(firstName: String) {
        val intent = Intent(this, DashBoardActivity::class.java)
        intent.putExtra("EXTRA_FIRST_NAME", firstName)
        intent.putExtra("EXTRA_ACCESS_TOKEN", authViewModel.accessToken.value)
        startActivity(intent)
    }
}
