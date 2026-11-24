package com.example.mywaypointsdui.shared


import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlinx.serialization.Serializable

object SDUIComponentSerializer : KSerializer<SDUIComponent> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("SDUIComponent") {
            element<String>("id")
            element("type", ComponentType.serializer().descriptor)
            element<Boolean>("enabled")
            element("data", JsonElement.serializer().descriptor)
        }

    override fun deserialize(decoder: Decoder): SDUIComponent {

        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException(
                "SDUIComponentSerializer only supports JSON"
            )

        val json = jsonDecoder.json

        // Read the entire component as a JSON object
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject

        // Decode common properties
        val id = json.decodeFromJsonElement<String>(
            jsonObject.getValue("id")
        )

        val type = json.decodeFromJsonElement<ComponentType>(
            jsonObject.getValue("type")
        )

        val enabled = json.decodeFromJsonElement<Boolean>(
            jsonObject.getValue("enabled")
        )

        // Get the component-specific data
        val dataElement = jsonObject["data"]

        val data = when (type) {

            ComponentType.Banner ->
                dataElement?.let {
                    json.decodeFromJsonElement(
                        ComponentData.Banner.serializer(), it
                    )
                }

            ComponentType.SearchBar ->
                dataElement?.let {
                    json.decodeFromJsonElement(
                        ComponentData.SearchBar.serializer(), it
                    )
                }

            ComponentType.ProductCarousel ->
                dataElement?.let {
                    json.decodeFromJsonElement(
                        ComponentData.ProductCarousel.serializer(), it
                    )
                }

            is ComponentType.Unknown -> null
        }

        return SDUIComponent(
            id = id,
            type = type,
            enabled = enabled,
            data = data
        )
    }

    override fun serialize(
        encoder: Encoder,
        value: SDUIComponent
    ) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw SerializationException(
                "SDUIComponentSerializer only supports JSON"
            )

        val json = jsonEncoder.json

        val dataElement = when (val data = value.data) {
            is ComponentData.Banner ->
                json.encodeToJsonElement(
                    ComponentData.Banner.serializer(), data
                )

            is ComponentData.SearchBar ->
                json.encodeToJsonElement(
                    ComponentData.SearchBar.serializer(), data
                )

            is ComponentData.ProductCarousel ->
                json.encodeToJsonElement(
                    ComponentData.ProductCarousel.serializer(), data
                )

            null -> JsonNull
        }

        val jsonObject = buildJsonObject {
            put("id", value.id)
            put(
                "type",
                json.encodeToJsonElement(
                    ComponentType.serializer(), value.type
                )
            )
            put("enabled", value.enabled)
            put("data", dataElement)
        }

        jsonEncoder.encodeJsonElement(jsonObject)
    }
}
