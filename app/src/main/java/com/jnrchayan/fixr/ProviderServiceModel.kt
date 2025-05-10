package com.jnrchayan.fixr

import java.io.Serializable


data class ProviderServiceModel(
    val serviceId: String = "",
    val title: String = "",
    val category: String = "",
    val description: String = "",
    val maxPrice: String = "",
    val minPrice: String = "",
    val availableDays: List<String> = emptyList(),
    val availableTime: String = "",
    val location: String = "",
    val phone: String = "",
    val providerId: String = "" // Add providerId if it's needed
) : Serializable