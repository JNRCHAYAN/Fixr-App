package com.jnrchayan.fixr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProviderServiceAdapter(
    private val services: List<ProviderServiceModel>,  // Update type to ProviderServiceModel
    private val onEditClick: (ProviderServiceModel) -> Unit,
    private val onDeleteClick: (ProviderServiceModel) -> Unit
) : RecyclerView.Adapter<ProviderServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.provider_item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.bind(service)
    }

    override fun getItemCount(): Int = services.size

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.serviceTitle)
        private val category: TextView = itemView.findViewById(R.id.serviceCategory)
        private val description: TextView = itemView.findViewById(R.id.serviceDescription)
        private val maxPrice: TextView = itemView.findViewById(R.id.serviceMaxPrice)  // If needed
        private val minPrice: TextView = itemView.findViewById(R.id.serviceMinPrice)  // If needed

        fun bind(service: ProviderServiceModel) {
            title.text = service.title
            category.text = service.category
            description.text = service.description
            maxPrice.text = service.maxPrice  // Bind maxPrice if needed
            minPrice.text = service.minPrice  // Bind minPrice if needed

            itemView.findViewById<View>(R.id.editButton).setOnClickListener {
                onEditClick(service)
            }

            itemView.findViewById<View>(R.id.deleteButton).setOnClickListener {
                onDeleteClick(service)
            }
        }
    }
}
