package com.jnrchayan.fixr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyServiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ServiceAdapter
    private lateinit var services: MutableList<ServiceModel>
    private lateinit var filteredServices: MutableList<ServiceModel>

    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerLocation: Spinner
    private lateinit var editTextSearch: EditText

    private lateinit var databaseRef: DatabaseReference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerLocation = findViewById(R.id.spinnerLocation)
        editTextSearch = findViewById(R.id.editTextSearch)

        services = mutableListOf()
        filteredServices = mutableListOf()
        adapter = ServiceAdapter(filteredServices)
        recyclerView.adapter = adapter

        loadServices()
        setupFilters()
    }

    private fun loadServices() {
        if (uid == null) return
        databaseRef = FirebaseDatabase.getInstance()
            .getReference("ServiceProviders").child(uid).child("servicelist")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                services.clear()
                for (data in snapshot.children) {
                    val service = data.getValue(ServiceModel::class.java)
                    if (service != null) {
                        services.add(service)
                    }
                }

                setupSpinnerValues()
                filterServices()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyServiceListActivity, "Failed to load services", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupFilters() {
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: android.view.View, position: Int, id: Long
            ) {
                filterServices()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: android.view.View, position: Int, id: Long
            ) {
                filterServices()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterServices()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupSpinnerValues() {
        val categoryList = services.map { it.category }.toSet().toMutableList()
        val locationList = services.map { it.location }.toSet().toMutableList()

        categoryList.sort()
        locationList.sort()
        categoryList.add(0, "All")
        locationList.add(0, "All")

        spinnerCategory.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryList)
        spinnerLocation.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locationList)
    }

    private fun filterServices() {
        val selectedCategory = spinnerCategory.selectedItem?.toString() ?: "All"
        val selectedLocation = spinnerLocation.selectedItem?.toString() ?: "All"
        val queryText = editTextSearch.text.toString().trim().lowercase()

        filteredServices.clear()
        filteredServices.addAll(
            services.filter { service ->
                (selectedCategory == "All" || service.category.equals(selectedCategory, true)) &&
                        (selectedLocation == "All" || service.location.equals(selectedLocation, true)) &&
                        (queryText.isEmpty() || service.title.contains(queryText, ignoreCase = true))
            }
        )
        adapter.notifyDataSetChanged()
    }
}
