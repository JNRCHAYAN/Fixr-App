package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class first_home : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_home) // Set the splash screen layout

        // Find buttons by ID
        val loginButton = findViewById<Button>(R.id.login)
        val signupButton = findViewById<Button>(R.id.signup)


        // Handle Login Button Click
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Handle Signup Button Click
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
