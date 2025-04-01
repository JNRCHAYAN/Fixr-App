package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class mainhome : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_home)

        val drawerLayout: DrawerLayout = findViewById(R.id.draweLayout)
        val navView: NavigationView = findViewById(R.id.nav_View)
        val profileView = findViewById<LinearLayout>(R.id.profileLayout)
        val serviceFind = findViewById<LinearLayout>(R.id.serviceLayout)

        val userNameTextView = findViewById<TextView>(R.id.userName) // Add this in your XML
        val userEmailTextView = findViewById<TextView>(R.id.userEmail) // Add this in your XML

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!

        // Display user's name and email
        userNameTextView.text = user.displayName ?: "No Name Found"
        userEmailTextView.text = user.email ?: "No Email Found"

        profileView.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }

        serviceFind.setOnClickListener {
            val intent = Intent(this, service_find::class.java)
            startActivity(intent)
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showToast("Home Selected")
                R.id.nav_messasge -> showToast("Contact Selected")
                R.id.nav_sync -> showToast("Service Selected")
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
