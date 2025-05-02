package com.jnrchayan.fixr

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class add_service : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var serviceTitle: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var description: EditText
    private lateinit var minPrice: EditText
    private lateinit var maxPrice: EditText
    private lateinit var timeButton: Button
    private lateinit var location: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var submitButton: Button

    private lateinit var checkBoxes: List<CheckBox>
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        serviceTitle = findViewById(R.id.editTextServiceTitle)
        categorySpinner = findViewById(R.id.spinnerCategory)
        description = findViewById(R.id.editTextDescription)
        minPrice = findViewById(R.id.editTextMinPrice)
        maxPrice = findViewById(R.id.editTextMaxPrice)
        timeButton = findViewById(R.id.buttonSelectTime)
        location = findViewById(R.id.editTextLocation)
        phone = findViewById(R.id.editTextPhone)
        email = findViewById(R.id.editTextEmail)
        submitButton = findViewById(R.id.buttonSubmit)

        // CheckBoxes
        checkBoxes = listOf(
            findViewById(R.id.checkboxMonday),
            findViewById(R.id.checkboxTuesday),
            findViewById(R.id.checkboxWednesday),
            findViewById(R.id.checkboxThursday),
            findViewById(R.id.checkboxFriday),
            findViewById(R.id.checkboxSaturday),
            findViewById(R.id.checkboxSunday)
        )

        // Time picker
        timeButton.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, hourOfDay, minuteOfDay ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                timeButton.text = "Available at: $selectedTime"
            }, hour, minute, true).show()
        }

        // Submit button
        submitButton.setOnClickListener {
            saveServiceToDatabase()
        }
    }

    private fun saveServiceToDatabase() {
        val user = firebaseAuth.currentUser
        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = user.uid
        val title = serviceTitle.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()
        val desc = description.text.toString().trim()
        val min = minPrice.text.toString().trim()
        val max = maxPrice.text.toString().trim()
        val loc = location.text.toString().trim()
        val phoneNumber = phone.text.toString().trim()
        val emailText = email.text.toString().trim()

        val availableDays = checkBoxes.filter { it.isChecked }.map { it.text.toString() }

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(min) ||
            TextUtils.isEmpty(max) || TextUtils.isEmpty(loc) || TextUtils.isEmpty(phoneNumber) ||
            selectedTime.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val serviceRef = FirebaseDatabase.getInstance()
            .getReference("ServiceProviders")
            .child("Users")
            .child(uid)
            .child("services")

        val serviceId = serviceRef.push().key ?: return

        val serviceData = mapOf(
            "serviceId" to serviceId,
            "title" to title,
            "category" to category,
            "description" to desc,
            "minPrice" to min,
            "maxPrice" to max,
            "time" to selectedTime,
            "location" to loc,
            "phone" to phoneNumber,
            "email" to emailText,
            "availableDays" to availableDays
        )

        serviceRef.child(serviceId)
            .setValue(serviceData)
            .addOnSuccessListener {
                Toast.makeText(this, "Service added successfully!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add service", Toast.LENGTH_SHORT).show()
            }
    }


    private fun clearFields() {
        serviceTitle.text.clear()
        description.text.clear()
        minPrice.text.clear()
        maxPrice.text.clear()
        location.text.clear()
        phone.text.clear()
        email.text.clear()
        selectedTime = ""
        timeButton.text = "Select Available Time"
        checkBoxes.forEach { it.isChecked = false }
    }
}
