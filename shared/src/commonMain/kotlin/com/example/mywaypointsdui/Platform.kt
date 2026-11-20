package com.example.mywaypointsdui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform