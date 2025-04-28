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
        setContentView(R.layout.first_home)


        val loginButton = findViewById<Button>(R.id.login)
        val signupButton = findViewById<Button>(R.id.signup)



        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, mainhome::class.java)
            startActivity(intent)
            finish()
        } else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
