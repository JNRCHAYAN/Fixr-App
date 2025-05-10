package com.jnrchayan.fixr

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProviderServicesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProviderServiceAdapter
    private val serviceList = mutableListOf<ProviderServiceModel>()
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    // Initialize Firebase Auth and Database Reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provider_service_services)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("ServiceProviders")

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProviderServiceAdapter(
            serviceList,
            onEditClick = { service -> showEditDialog(service) },
            onDeleteClick = { service -> deleteService(service) }
        )

        recyclerView.adapter = adapter

        loadServicesByProviderId()  // Load services for the logged-in provider
    }

    // Load services by the logged-in provider's ID
    private fun loadServicesByProviderId() {
        val targetProviderId = auth.currentUser?.uid // Get the logged-in user's UID
        if (targetProviderId != null) {
            database.child(targetProviderId).child("servicelist")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        serviceList.clear()
                        for (serviceSnap in snapshot.children) {
                            val service = serviceSnap.getValue(ProviderServiceModel::class.java)
                            if (service != null) {
                                serviceList.add(service)
                            }
                        }

                        // Log the services retrieved
                        Log.d("ProviderServicesActivity", "Loaded services: ${serviceList.size}")

                        if (serviceList.isEmpty()) {
                            Toast.makeText(this@ProviderServicesActivity, "No services found for this provider.", Toast.LENGTH_SHORT).show()
                        }

                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@ProviderServicesActivity, "Failed to load services.", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    // Delete a service from the database
    private fun deleteService(service: ProviderServiceModel) {
        val targetProviderId = auth.currentUser?.uid // Get the logged-in user's UID
        if (targetProviderId != null) {
            database.child(targetProviderId).child("servicelist").child(service.serviceId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Service deleted", Toast.LENGTH_SHORT).show()
                    loadServicesByProviderId()  // Reload the services after deletion
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Show dialog to edit a service
    private fun showEditDialog(service: ProviderServiceModel) {
        val view = LayoutInflater.from(this).inflate(R.layout.provider_dialog_edit_service, null)
        val titleEdit = view.findViewById<EditText>(R.id.editTitle)
        val descriptionEdit = view.findViewById<EditText>(R.id.editDescription)
        val maxPriceEdit = view.findViewById<EditText>(R.id.editMaxPrice)
        val minPriceEdit = view.findViewById<EditText>(R.id.editMinPrice)
        val timeEdit = view.findViewById<EditText>(R.id.editTime)
        val locationEdit = view.findViewById<EditText>(R.id.editLocation)
        val phoneEdit = view.findViewById<EditText>(R.id.editPhone)

        // Populate the edit fields with existing service data
        titleEdit.setText(service.title)
        descriptionEdit.setText(service.description)
        maxPriceEdit.setText(service.maxPrice.toString())  // Ensure price is set correctly
        minPriceEdit.setText(service.minPrice.toString())
        timeEdit.setText(service.availableTime)
        locationEdit.setText(service.location)
        phoneEdit.setText(service.phone)

        AlertDialog.Builder(this)
            .setTitle("Edit Service")
            .setView(view)
            .setPositiveButton("Update") { _, _ ->
                // Collect updated data and create a new ProviderServiceModel
                val updatedService = service.copy(
                    title = titleEdit.text.toString(),
                    description = descriptionEdit.text.toString(),
                    maxPrice = (maxPriceEdit.text.toString().toDoubleOrNull() ?: 0.0).toString(),
                    minPrice = (minPriceEdit.text.toString().toDoubleOrNull() ?: 0.0).toString(),
                    availableTime = timeEdit.text.toString(),
                    location = locationEdit.text.toString(),
                    phone = phoneEdit.text.toString()
                )

                // Update the service in Firebase
                val targetProviderId = auth.currentUser?.uid // Get the logged-in user's UID
                if (targetProviderId != null) {
                    database.child(targetProviderId).child("servicelist").child(service.serviceId)
                        .setValue(updatedService)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                            loadServicesByProviderId()  // Reload the services after updating
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
