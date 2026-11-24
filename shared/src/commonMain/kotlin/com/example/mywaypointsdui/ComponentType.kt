package com.example.mywaypointsdui.shared

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.SerializationException

@Serializable(with = ComponentTypeSerializer::class)
sealed class ComponentType {
    data object Banner : ComponentType()
    data object SearchBar : ComponentType()
    data object ProductCarousel : ComponentType()
    data class Unknown(val value: String) : ComponentType()
}

object ComponentTypeSerializer : KSerializer<ComponentType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ComponentType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ComponentType {
        return when (val value = decoder.decodeString()) {
            "banner" -> ComponentType.Banner
            "searchBar" -> ComponentType.SearchBar
            "productCarousel" -> ComponentType.ProductCarousel
            else -> ComponentType.Unknown(value)
        }
    }


    override fun serialize(
        encoder: Encoder,
        value: ComponentType
    ) {
        val stringValue = when (value) {
            ComponentType.Banner -> "banner"
            ComponentType.SearchBar -> "searchBar"
            ComponentType.ProductCarousel -> "productCarousel"
            is ComponentType.Unknown -> value.value
        }

        encoder.encodeString(stringValue)
    }
}