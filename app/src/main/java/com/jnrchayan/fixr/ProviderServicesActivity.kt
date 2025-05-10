package com.jnrchayan.fixr

import android.app.AlertDialog
import android.os.Bundle
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
    private val serviceList = mutableListOf<ProviderServiceModel>() // Updated to use ProviderServiceModel
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provider_service_services)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("ServiceProviders")

        // Ensure the user is logged in
        if (auth.currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish() // Finish the activity if the user is not logged in
            return
        }

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProviderServiceAdapter(serviceList,
            onEditClick = { service -> showEditDialog(service) },
            onDeleteClick = { service -> deleteService(service) })

        recyclerView.adapter = adapter

        loadServices()
    }

    private fun loadServices() {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val userServicesRef = database.child(currentUserId).child("servicelist")

        userServicesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                serviceList.clear()
                if (!snapshot.exists()) {
                    Toast.makeText(this@ProviderServicesActivity, "No services found.", Toast.LENGTH_SHORT).show()
                }
                for (data in snapshot.children) {
                    val service = data.getValue(ProviderServiceModel::class.java) // Updated to use ProviderServiceModel
                    if (service != null) {
                        serviceList.add(service)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProviderServicesActivity, "Failed to load services.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteService(service: ProviderServiceModel) { // Updated to use ProviderServiceModel
        val currentUserId = auth.currentUser?.uid ?: return
        database.child(currentUserId).child("servicelist").child(service.serviceId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Service deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showEditDialog(service: ProviderServiceModel) { // Updated to use ProviderServiceModel
        val view = LayoutInflater.from(this).inflate(R.layout.provider_dialog_edit_service, null)
        val titleEdit = view.findViewById<EditText>(R.id.editTitle)
        val descriptionEdit = view.findViewById<EditText>(R.id.editDescription)
        val maxPriceEdit = view.findViewById<EditText>(R.id.editMaxPrice)
        val minPriceEdit = view.findViewById<EditText>(R.id.editMinPrice)
        val timeEdit = view.findViewById<EditText>(R.id.editTime)
        val locationEdit = view.findViewById<EditText>(R.id.editLocation)
        val phoneEdit = view.findViewById<EditText>(R.id.editPhone)

        titleEdit.setText(service.title)
        descriptionEdit.setText(service.description)
        maxPriceEdit.setText(service.maxPrice)
        minPriceEdit.setText(service.minPrice)
        timeEdit.setText(service.time)
        locationEdit.setText(service.location)
        phoneEdit.setText(service.phone)

        AlertDialog.Builder(this)
            .setTitle("Edit Service")
            .setView(view)
            .setPositiveButton("Update") { _, _ ->
                val currentUserId = auth.currentUser?.uid ?: return@setPositiveButton
                val updatedService = service.copy(
                    title = titleEdit.text.toString(),
                    description = descriptionEdit.text.toString(),
                    maxPrice = maxPriceEdit.text.toString(),
                    minPrice = minPriceEdit.text.toString(),
                    time = timeEdit.text.toString(),
                    location = locationEdit.text.toString(),
                    phone = phoneEdit.text.toString()
                )
                database.child(currentUserId).child("servicelist").child(service.serviceId).setValue(updatedService)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
