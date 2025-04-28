package com.jnrchayan.fixr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class profile : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference


    private lateinit var fullName: EditText
    private lateinit var username: EditText
    private lateinit var address: EditText
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)


        firebaseAuth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Users")


        fullName = findViewById(R.id.full_name)
        username = findViewById(R.id.username)
        address = findViewById(R.id.address)
        updateButton = findViewById(R.id.update_button)

        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }


        databaseRef.child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = snapshot.getValue(User::class.java)
                user?.let {
                    fullName.setText(it.fullName ?: "")
                    username.setText(it.username ?: "")
                    address.setText(it.address ?: "")
                }
            } else {
                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to fetch user data: ${it.message}", Toast.LENGTH_SHORT).show()
        }


        updateButton.setOnClickListener {
            updateProfile(userId)
        }
    }

    private fun updateProfile(userId: String) {
        val fname = fullName.text.toString().trim()
        val uname = username.text.toString().trim()
        val userAddress = address.text.toString().trim()

        if (fname.isEmpty() || uname.isEmpty() || userAddress.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }


        val updatedUser = mapOf(
            "fullName" to fname,
            "username" to uname,
            "address" to userAddress
        )


        databaseRef.child(userId).updateChildren(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
