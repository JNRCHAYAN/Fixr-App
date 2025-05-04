package com.jnrchayan.fixr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServiceAdapter(private val services: List<ServiceModel>) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvDays: TextView = itemView.findViewById(R.id.tvDays)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.tvTitle.text = service.title
        holder.tvCategory.text = "Category: ${service.category}"
        holder.tvPrice.text = "Price: ${service.priceMin} - ${service.priceMax}"
        holder.tvLocation.text = "Location: ${service.location}"
        holder.tvDays.text = "Available: ${service.availableDays.joinToString(", ")}"
    }

    override fun getItemCount(): Int = services.size
}
