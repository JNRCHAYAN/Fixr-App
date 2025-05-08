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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvCategory = findViewById(R.id.tvCategory)
        tvLocation = findViewById(R.id.tvLocation)
        tvPrice = findViewById(R.id.tvPrice)
        tvDays = findViewById(R.id.tvDays)
        tvDescription = findViewById(R.id.tvDescription)
        btnCall = findViewById(R.id.btnCall)

        // Receive Serializable object
        val service = intent.getSerializableExtra("service") as? ServiceModel

        service?.let { serviceData ->
            tvTitle.text = serviceData.title
            tvCategory.text = "Category: ${serviceData.category}"
            tvLocation.text = "Location: ${serviceData.location}"
            tvPrice.text = "Price: ${serviceData.minPrice} - ${serviceData.maxPrice} Taka"
            tvDays.text = "Available Days: ${serviceData.availableDays.joinToString(", ")}"
            tvDescription.text = "Description: ${serviceData.description}"

            // Set up call button
            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${serviceData.phone}")
                startActivity(intent)
            }
        } ?: run {
            // If no service data was passed, finish the activity
            finish()
        }
    }
}
