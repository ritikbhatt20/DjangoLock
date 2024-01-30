package com.example.djangoauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val welcomeMessageTextView = findViewById<TextView>(R.id.welcomeMessageTextView)
        val accessTokenTextView = findViewById<TextView>(R.id.accessTokenTextView)

        // Get the user's first name and access token from Intent
        val firstName = intent.getStringExtra("EXTRA_FIRST_NAME")
        val accessToken = intent.getStringExtra("EXTRA_ACCESS_TOKEN")

        // Display the welcome message and access token
        welcomeMessageTextView.text = "Welcome, $firstName!"
        accessTokenTextView.text = "Access Token: $accessToken"
    }
}
