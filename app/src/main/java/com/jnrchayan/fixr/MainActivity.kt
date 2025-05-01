package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashscreen)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, after_flash::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
