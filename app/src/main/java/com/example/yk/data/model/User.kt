package com.example.yk.data.model

/**
 * Data class that captures user information
 */
data class User(
    var name: String? = null,
    var surname: String? = null,
    var mail: String = "",
    var photoUrl: String? = null,
    var photoUri: String? = null
)
