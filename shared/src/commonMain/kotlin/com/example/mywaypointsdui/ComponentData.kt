package com.example.mywaypointsdui.shared

import kotlinx.serialization.Serializable

@Serializable
sealed class ComponentData {

    @Serializable
    data class Banner(
        val title: String,
        val subtitle: String,
        val imageUrl: String
    ) : ComponentData()

    @Serializable
    data class SearchBar(
        val placeholder: String
    ) : ComponentData()

    @Serializable
    data class ProductCarousel(
        val title: String,
        val products: List<ProductData>
    ) : ComponentData()
}