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
    private lateinit var selectTimeButton: Button
    private lateinit var location: EditText
    private lateinit var phone: EditText
    private lateinit var submitButton: Button

    private lateinit var checkboxMonday: CheckBox
    private lateinit var checkboxTuesday: CheckBox
    private lateinit var checkboxWednesday: CheckBox
    private lateinit var checkboxThursday: CheckBox
    private lateinit var checkboxFriday: CheckBox
    private lateinit var checkboxSaturday: CheckBox
    private lateinit var checkboxSunday: CheckBox

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
        selectTimeButton = findViewById(R.id.buttonSelectTime)
        location = findViewById(R.id.editTextLocation)
        phone = findViewById(R.id.editTextPhone)
        submitButton = findViewById(R.id.buttonSubmit)

        checkboxMonday = findViewById(R.id.checkboxMonday)
        checkboxTuesday = findViewById(R.id.checkboxTuesday)
        checkboxWednesday = findViewById(R.id.checkboxWednesday)
        checkboxThursday = findViewById(R.id.checkboxThursday)
        checkboxFriday = findViewById(R.id.checkboxFriday)
        checkboxSaturday = findViewById(R.id.checkboxSaturday)
        checkboxSunday = findViewById(R.id.checkboxSunday)

        checkBoxes = listOf(
            checkboxMonday, checkboxTuesday, checkboxWednesday,
            checkboxThursday, checkboxFriday, checkboxSaturday, checkboxSunday
        )

        // Setup spinner
        val categories = arrayOf("Plumber", "Electrician", "Cleaner", "Technician", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.adapter = adapter

        // Time picker
        selectTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                selectTimeButton.text = "Available at: $selectedTime"
            }, hour, minute, true)

            timePickerDialog.show()
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
        val availableDays = checkBoxes.filter { it.isChecked }.map { it.text.toString() }

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(min) ||
            TextUtils.isEmpty(max) || TextUtils.isEmpty(loc) || TextUtils.isEmpty(phoneNumber) ||
            selectedTime.isEmpty() || availableDays.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val serviceRef = FirebaseDatabase.getInstance().getReference("servicelist")
        val serviceId = serviceRef.push().key ?: return

        val serviceData = mapOf(
            "serviceId" to serviceId,
            "providerId" to uid,
            "title" to title,
            "category" to category,
            "description" to desc,
            "minPrice" to min,
            "maxPrice" to max,
            "availableTime" to selectedTime,
            "availableDays" to availableDays,
            "location" to loc,
            "phone" to phoneNumber
        )

        serviceRef.child(serviceId)
            .setValue(serviceData)
            .addOnSuccessListener {
                Toast.makeText(this, "Service added successfully!", Toast.LENGTH_SHORT).show()
                clearFields()

                // Save under provider node too
                saveServiceUnderProvider(uid, serviceId, serviceData)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add service", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveServiceUnderProvider(uid: String, serviceId: String, serviceData: Map<String, Any?>) {
        val providerServiceRef = FirebaseDatabase.getInstance()
            .getReference("ServiceProviders")
            .child(uid)
            .child("servicelist")
            .child(serviceId)

        providerServiceRef.setValue(serviceData)
            .addOnSuccessListener {
                Toast.makeText(this, "Also saved under provider", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save under provider", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        serviceTitle.setText("")
        description.setText("")
        minPrice.setText("")
        maxPrice.setText("")
        location.setText("")
        phone.setText("")
        selectedTime = ""
        selectTimeButton.text = "Select Available Time"
        checkBoxes.forEach { it.isChecked = false }
        categorySpinner.setSelection(0)
    }
}
