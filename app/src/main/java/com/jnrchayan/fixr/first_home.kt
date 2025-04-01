package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class first_home : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var firebaseAuth: FirebaseAuth
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


        firebaseAuth = FirebaseAuth.getInstance()

        // Check if user is already logged in
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, mainhome::class.java)
            startActivity(intent)
            finish() // Close first_home so it doesn't stay in the back stack
        } else {
            // If not logged in, go to login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
