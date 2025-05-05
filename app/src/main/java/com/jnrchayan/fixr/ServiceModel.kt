package com.jnrchayan.fixr

data class ServiceModel(val title: String = "",
    val category: String = "",
    val description: String = "",
    val maxPrice: String = "",
    val minPrice: String = "",
    val availableDays: List<String> = emptyList(),
    val time: String = "",
    val location: String = "",
    val phone: String = "")
