package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
  // Chayan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Set the login screen layout

        firebaseAuth = FirebaseAuth.getInstance()

        // Find the TextView (signup link) by ID after the layout is set
        val signup_link = findViewById<TextView>(R.id.signup_link)
        val email = findViewById<TextView>(R.id.email)
        val pass = findViewById<TextView>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            // Get the text from the TextViews and check if they are not empty
            val emailText = email.text.toString()
            val passText = pass.text.toString()

            if (emailText.isNotEmpty() && passText.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(emailText, passText).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, mainhome::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Signup Link Click
        signup_link.setOnClickListener {
            // Navigate to SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
