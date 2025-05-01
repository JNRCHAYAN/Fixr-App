package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jnrchayan.fixr.databinding.SignupBinding

class SignupActivity : ComponentActivity() {
    private lateinit var binding: SignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        // 👇 Save to a separate "Users" node in Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().reference.child("Users")

        binding.signupButton.setOnClickListener {
            val fname = binding.fullName.text.toString()
            val uname = binding.username.text.toString()
            val pass = binding.password.text.toString()
            val email = binding.email.text.toString()
            val address = binding.address.text.toString()

            if (fname.isNotEmpty() && uname.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        val user = hashMapOf(
                            "fullName" to fname,
                            "username" to uname,
                            "email" to email,
                            "address" to address
                        )
                        userId?.let {
                            databaseRef.child(it).setValue(user).addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, mainhome::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, dbTask.exception.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to Login page
        val loginLink = findViewById<TextView>(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
