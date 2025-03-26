package com.jnrchayan.fixr

import android.R.id.toggle
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class test : AppCompatActivity() {

    lateinit var toogle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        val  drawerLayout : DrawerLayout = findViewById(R.id.draweLayout)
        val navView : NavigationView = findViewById(R.id.nav_View)

        toogle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showToast("Home Selected")
                R.id.nav_messasge -> showToast("Contact Selected")
                R.id.nav_sync -> showToast("Service Selected")
                else -> false
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Handle Action Bar Toggle Clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)) {
            return true
        }
        return TODO("Provide the return value")
    }

    // Show Toast Messages
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    }

