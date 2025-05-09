package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class mainhome : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_home)

        val drawerLayout: DrawerLayout = findViewById(R.id.draweLayout)
        val navView: NavigationView = findViewById(R.id.nav_View)
        val profileView = findViewById<LinearLayout>(R.id.profileLayout)
        val serviceFind = findViewById<LinearLayout>(R.id.serviceLayout)
        val logoutButton = findViewById<LinearLayout>(R.id.logoutButton)
        val serach = findViewById<TextView>(R.id.textView7)
        val LayoutClick = findViewById<ConstraintLayout>(R.id.constraintLayout)


        val userNameTextView = findViewById<TextView>(R.id.userName)
        val userEmailTextView = findViewById<TextView>(R.id.userEmail)

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!


        userEmailTextView.text = user.email ?: "No Email Found"


        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.uid)


        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val fullName = snapshot.child("fullName").getValue(String::class.java)
                    userNameTextView.text = fullName ?: "No Name Found"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@mainhome, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        })

        profileView.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }

        serviceFind.setOnClickListener {
            val intent = Intent(this, ServiceListActivity::class.java)
            startActivity(intent)
        }
        serach.setOnClickListener {
            val intent = Intent(this, ServiceListActivity::class.java)
            startActivity(intent)
        }
        LayoutClick.setOnClickListener {
            val intent = Intent(this, ServiceListActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, first_home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
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
