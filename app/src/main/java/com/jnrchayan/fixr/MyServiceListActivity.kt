package com.jnrchayan.fixr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jnrchayan.fixr.ServiceModel

class MyServiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceList: ArrayList<ServiceModel>
    private lateinit var adapter: ServiceAdapter
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        recyclerView = findViewById(R.id.recyclerViewServices)
        recyclerView.layoutManager = LinearLayoutManager(this)
        serviceList = ArrayList()
        adapter = ServiceAdapter(serviceList)
        recyclerView.adapter = adapter

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val currentUid = currentUser.uid
            databaseRef = FirebaseDatabase.getInstance().getReference("servicelist")
            loadMyServices(currentUid)
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMyServices(uid: String) {
        databaseRef.orderByChild("providerUid").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    serviceList.clear()
                    for (serviceSnap in snapshot.children) {
                        val service = serviceSnap.getValue(ServiceModel::class.java)
                        service?.let { serviceList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MyServiceListActivity, "Failed to load services", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
