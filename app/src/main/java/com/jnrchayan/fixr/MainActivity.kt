package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashscreen) // Set the splash screen layout

        // Delay for 5 seconds and then move to HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, first_home::class.java)
            startActivity(intent)
            finish() // Close splash screen activity
        }, 1000) // 5000ms = 5 seconds
    }
}
