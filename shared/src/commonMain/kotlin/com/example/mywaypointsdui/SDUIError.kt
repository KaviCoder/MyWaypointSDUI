package com.example.mywaypointsdui.shared

sealed class SDUIError : Exception() {

    data class UnsupportedSchemaVersion(
        val receivedVersion: Int
    ) : SDUIError()
}