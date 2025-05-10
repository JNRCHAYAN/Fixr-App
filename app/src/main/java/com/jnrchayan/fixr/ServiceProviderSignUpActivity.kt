package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jnrchayan.fixr.R

class ServiceProviderSignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var fullName: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var address: EditText
    private lateinit var serviceType: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_sign_up)

        mAuth = FirebaseAuth.getInstance()

        fullName = findViewById(R.id.full_name)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)
        serviceType = findViewById(R.id.service_type)
        signupButton = findViewById(R.id.signup_button)

        signupButton.setOnClickListener {
            val name = fullName.text.toString()
            val user = username.text.toString()
            val pass = password.text.toString()
            val emailText = email.text.toString()
            val addr = address.text.toString()
            val service = serviceType.text.toString()

            if (name.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty()
                && emailText.isNotEmpty() && addr.isNotEmpty() && service.isNotEmpty()) {
                signUpServiceProvider(name, user, pass, emailText, addr, service)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpServiceProvider(
        name: String,
        user: String,
        pass: String,
        emailText: String,
        addr: String,
        service: String
    ) {
        mAuth.createUserWithEmailAndPassword(emailText, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid
                    val serviceProvider = hashMapOf(
                        "fullName" to name,
                        "username" to user,
                        "email" to emailText,
                        "address" to addr,
                        "serviceType" to service
                    )

                    userId?.let {
                        val dbRef = FirebaseDatabase.getInstance().reference.child("ServiceProviders")
                        dbRef.child(it).setValue(serviceProvider).addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                Toast.makeText(this, "Service Provider Signup Successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, ServiceProviderHomeActivity::class.java)) // Navigate as needed
                                finish()
                            } else {
                                Toast.makeText(this, "DB Error: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Signup Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
