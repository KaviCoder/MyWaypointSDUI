package com.example.mywaypointsdui.shared

import kotlinx.serialization.Serializable

@Serializable
data class ProductData(
    val id: String,
    val name: String,
    val price: Double
)