package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jnrchayan.fixr.service_provider_login
import com.jnrchayan.fixr.mainhome

class service_provider_login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_login)

        mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        signupLink = findViewById(R.id.signup_link)

        loginButton.setOnClickListener {
            val emailText = email.text.toString()
            val passText = password.text.toString()

            if (emailText.isNotEmpty() && passText.isNotEmpty()) {
                loginUser(emailText, passText)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        signupLink.setOnClickListener {
            // Navigate to the signup activity for service provider
            val intent = Intent(this, ServiceProviderSignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(emailText: String, passText: String) {
        mAuth.signInWithEmailAndPassword(emailText, passText)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success, navigate to the next screen (e.g., Service Dashboard)
                    val intent = Intent(this, ServiceProviderHomeActivity::class.java)
                    startActivity(intent)
                    finish()  // Close the login screen
                } else {
                    // If login fails, show error message
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
