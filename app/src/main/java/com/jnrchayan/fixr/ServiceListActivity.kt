package com.jnrchayan.fixr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ServiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceList: ArrayList<ServiceModel>
    private lateinit var adapter: ServiceAdapter
    private val databaseRef = FirebaseDatabase.getInstance().getReference("servicelist")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)
        serviceList = ArrayList()
        adapter = ServiceAdapter(serviceList)
        recyclerView.adapter = adapter

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
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ServiceListActivity, "Failed to load services", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
