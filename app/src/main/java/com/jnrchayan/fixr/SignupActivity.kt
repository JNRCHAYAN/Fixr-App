
package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.jnrchayan.fixr.databinding.SignupBinding

class SignupActivity : ComponentActivity() {
    private lateinit var binding: SignupBinding
    private lateinit var  firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener(){
            val fname = binding.fullName.toString()
            val uname = binding.username.toString()
            val pass = binding.password.text.toString()
            val email = binding.email.text.toString()
            val address = binding.address.text.toString()

            if(fname.isNotEmpty() && uname.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() )
            {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(){
                        if(it.isSuccessful){
                            Toast.makeText(this, "SignUp Susscessfully",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, mainhome:: class.java)
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            else
            {
                Toast.makeText(this, "Please fill all the information",Toast.LENGTH_SHORT).show()
            }


        }


        val login_link = findViewById<TextView>(R.id.login_link)

        // Handle Signup Link Click
        login_link.setOnClickListener {
            // Navigate to SignupActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}