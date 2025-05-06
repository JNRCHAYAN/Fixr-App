package com.jnrchayan.fixr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ServiceDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDays: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnCall: Button

    private var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvCategory = findViewById(R.id.tvCategory)
        tvLocation = findViewById(R.id.tvLocation)
        tvPrice = findViewById(R.id.tvPrice)
        tvDays = findViewById(R.id.tvDays)
        tvDescription = findViewById(R.id.tvDescription)
        btnCall = findViewById(R.id.btnCall)

        // Use Serializable to get the object
        val service = intent.getSerializableExtra("service") as? ServiceModel

        service?.let {
            tvTitle.text = it.title
            tvCategory.text = "Category: ${it.category}"
            tvLocation.text = "Location: ${it.location}"
            tvPrice.text = "Price: ${it.maxPrice}"
            tvDays.text = "Available Days: ${it.availableDays}"
            tvDescription.text = "Description: ${it.description}"
            phoneNumber = it.phone
        }

        btnCall.setOnClickListener {
            phoneNumber?.let { number ->
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                startActivity(intent)
            }
        }
    }
}
