package com.jnrchayan.fixr

import java.io.Serializable

data class ServiceModel(val title: String = "",
    val category: String = "",
    val description: String = "",
    val maxPrice: String = "",
    val minPrice: String = "",
    val availableDays: List<String> = emptyList(),
    val time: String = "",
    val location: String = "",
    val phone: String = "") : Serializable
