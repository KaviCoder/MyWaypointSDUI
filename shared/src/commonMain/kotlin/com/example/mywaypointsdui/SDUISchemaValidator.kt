package com.example.mywaypointsdui.shared

class SDUISchemaValidator {

    fun validate(response: ScreenResponse) {
        if (response.schemaVersion != SDUISchema.supportedVersion) {
            throw SDUIError.UnsupportedSchemaVersion(
                response.schemaVersion
            )
        }
    }
}