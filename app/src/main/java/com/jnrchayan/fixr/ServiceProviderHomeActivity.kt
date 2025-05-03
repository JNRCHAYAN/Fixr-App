package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.jnrchayan.fixr.add_service
import com.jnrchayan.fixr.mainhome
import com.jnrchayan.fixr.profile
import com.jnrchayan.fixr.service_provider_login
import java.util.Calendar

class ServiceProviderHomeActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var userNameText: TextView
    private lateinit var greetingText: TextView
    private lateinit var settingsButton: ImageView
    private lateinit var addServiceLayout: LinearLayout
    private lateinit var myServiceLayout: LinearLayout
    private lateinit var currentUser: FirebaseUser
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_home)

        // Firebase instances
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser ?: run {
            startActivity(Intent(this, service_provider_login::class.java))
            finish()
            return
        }

        // Initialize views
        userNameText = findViewById(R.id.userNameText)
        greetingText = findViewById(R.id.greetingText)
        settingsButton = findViewById(R.id.settingsButton)
        addServiceLayout = findViewById(R.id.addServiceLayout)
        myServiceLayout = findViewById(R.id.myServiceLayout)

        // Set greeting based on time
        greetingText.text = getGreetingMessage()

        // Get user data from Firebase Realtime Database
        fetchUserNameFromDatabase(currentUser.uid)

        // Navigation
        addServiceLayout.setOnClickListener {
            startActivity(Intent(this, add_service::class.java))
        }

        myServiceLayout.setOnClickListener {
            startActivity(Intent(this, mainhome::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, profile::class.java))
        }

        logoutButton = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, service_provider_login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }

    private fun getGreetingMessage(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Good Morning!"
            in 12..16 -> "Good Afternoon!"
            in 17..20 -> "Good Evening!"
            else -> "Good Night!"
        }
    }

    private fun fetchUserNameFromDatabase(uid: String) {
        databaseRef = FirebaseDatabase.getInstance().getReference("ServiceProviders").child(uid)

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("fullName").getValue(String::class.java)
                userNameText.text = name ?: "User"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read user name", error.toException())
                userNameText.text = "User"
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, service_provider_login::class.java))
            finish()
        }
    }


}
