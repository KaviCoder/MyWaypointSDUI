
package com.example.mywaypointsdui.shared

import kotlinx.serialization.Serializable

@Serializable
data class ScreenResponse(
    val schemaVersion: Int,
    val screen: String,
    val components: List<SDUIComponent>
)