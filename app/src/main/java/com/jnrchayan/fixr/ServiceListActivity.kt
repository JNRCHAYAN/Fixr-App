package com.jnrchayan.fixr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ServiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceList: ArrayList<ServiceModel>
    private lateinit var filteredList: ArrayList<ServiceModel>
    private lateinit var adapter: ServiceAdapter
    private lateinit var etSearch: EditText
    private val databaseRef = FirebaseDatabase.getInstance().getReference("servicelist")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        etSearch = findViewById(R.id.editTextSearch)

        serviceList = ArrayList()
        filteredList = ArrayList()
        adapter = ServiceAdapter(filteredList)
        recyclerView.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterServices(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

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
                filterServices(etSearch.text.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ServiceListActivity, "Failed to load services", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterServices(query: String) {
        val lowerCaseQuery = query.lowercase()
        filteredList.clear()
        for (service in serviceList) {
            if (service.title.lowercase().contains(lowerCaseQuery) ||
                service.category.lowercase().contains(lowerCaseQuery) ||
                service.location.lowercase().contains(lowerCaseQuery)
            ) {
                filteredList.add(service)
            }
        }
        adapter.notifyDataSetChanged()
    }
}
