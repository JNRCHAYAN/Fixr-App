package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class after_flash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.after_flash)

        val getservice = findViewById<Button>(R.id.g_service)
        val p_service = findViewById<Button>(R.id.p_service)


        getservice.setOnClickListener {
            val intent = Intent(this, first_home::class.java)
            startActivity(intent)
        }

        p_service.setOnClickListener {
            val intent = Intent(this, service_provider_login::class.java)
            startActivity(intent)
        }




    }
}