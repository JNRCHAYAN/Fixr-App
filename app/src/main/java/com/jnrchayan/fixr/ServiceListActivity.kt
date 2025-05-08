package com.jnrchayan.fixr

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.io.Serializable

class ServiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceList: ArrayList<ServiceModel>
    private lateinit var filteredList: ArrayList<ServiceModel>
    private lateinit var adapter: ServiceAdapter
    private lateinit var etSearch: EditText
    private lateinit var etLocation: EditText
    private lateinit var spinnerCategory: Spinner
    private val databaseRef = FirebaseDatabase.getInstance().getReference("servicelist")

    private val categoryList = listOf("All", "Electrician", "Plumber", "Carpenter", "Cleaner", "Mechanic")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        etSearch = findViewById(R.id.editTextSearch)
        etLocation = findViewById(R.id.editTextLocation)
        spinnerCategory = findViewById(R.id.spinnerCategory)

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        serviceList = ArrayList()
        filteredList = ArrayList()

        adapter = ServiceAdapter(filteredList) { selectedService ->
            // When item clicked, open ServiceDetailActivity
            val intent = Intent(this, ServiceDetailActivity::class.java)
            intent.putExtra("service", selectedService as Serializable)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val filterWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterServices()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etSearch.addTextChangedListener(filterWatcher)
        etLocation.addTextChangedListener(filterWatcher)
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                filterServices()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        loadServices()
    }

    private fun loadServices() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                serviceList.clear()
                for (serviceSnap in snapshot.children) {
                    val service = serviceSnap.getValue(ServiceModel::class.java)
                    service?.let { serviceList.add(it) }
                }
                filterServices()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ServiceListActivity, "Failed to load services", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterServices() {
        val query = etSearch.text.toString().lowercase()
        val locationQuery = etLocation.text.toString().lowercase()
        val selectedCategory = spinnerCategory.selectedItem.toString().lowercase()

        filteredList.clear()
        for (service in serviceList) {
            val matchesTitleOrDesc = service.title.lowercase().contains(query)
            val matchesLocation = locationQuery.isEmpty() || service.location.lowercase().contains(locationQuery)
            val matchesCategory = selectedCategory == "all" || service.category.lowercase() == selectedCategory

            if (matchesTitleOrDesc && matchesLocation && matchesCategory) {
                filteredList.add(service)
            }
        }
        adapter.notifyDataSetChanged()
    }
}
