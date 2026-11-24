
package com.example.mywaypointsdui.shared

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SDUITest {

    @Test
    fun decodeHomeScreenResponse() {
        val jsonString = """
            {
              "schemaVersion": 1,
              "screen": "home",
              "components": [
                {
                  "id": "home-search",
                  "type": "searchBar",
                  "enabled": true,
                  "data": {
                    "placeholder": "What are you looking for?"
                  }
                },
                {
                  "id": "welcome-banner",
                  "type": "banner",
                  "enabled": true,
                  "data": {
                    "title": "Welcome back!",
                    "subtitle": "Find everything for your next project.",
                    "imageUrl": "https://example.com/banner.jpg"
                  }
                },
                {
                  "id": "featured-products",
                  "type": "productCarousel",
                  "enabled": true,
                  "data": {
                    "title": "Popular products",
                    "products": [
                      {
                        "id": "p1",
                        "name": "Cordless Drill",
                        "price": 99.00
                      },
                      {
                        "id": "p2",
                        "name": "Tool Set",
                        "price": 49.00
                      }
                    ]
                  }
                },
                {
                  "id": "featured-products_",
                  "type": "productCarousel3",
                  "enabled": true,
                  "data": {}
                }
              ]
            }
        """.trimIndent()

        val json = Json {
            ignoreUnknownKeys = true
        }

        val response = json.decodeFromString<ScreenResponse>(jsonString)

        assertEquals(1, response.schemaVersion)
        assertEquals("home", response.screen)
        assertEquals(4, response.components.size)

        // Search bar
        val search = response.components[0]
        assertEquals(ComponentType.SearchBar, search.type)
        assertTrue(search.data is ComponentData.SearchBar)

        // Banner
        val banner = response.components[1]
        assertEquals(ComponentType.Banner, banner.type)
        assertTrue(banner.data is ComponentData.Banner)

        // Product carousel
        val carousel = response.components[2]
        assertEquals(ComponentType.ProductCarousel, carousel.type)
        assertTrue(carousel.data is ComponentData.ProductCarousel)

        // Unknown component
        val unknown = response.components[3]
        assertEquals(
            ComponentType.Unknown("productCarousel3"),
            unknown.type
        )
        assertNull(unknown.data)
    }
}
