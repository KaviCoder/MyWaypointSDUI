package com.example.mywaypointsdui.shared

import kotlinx.serialization.Serializable

@Serializable(with = SDUIComponentSerializer::class)
data class SDUIComponent(
    val id: String,
    val type: ComponentType,
    val enabled: Boolean,
    val data: ComponentData? = null
)