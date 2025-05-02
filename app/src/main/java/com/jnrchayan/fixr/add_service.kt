package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class add_service : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var firebaseAuth: FirebaseAuth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_service)


    }
}