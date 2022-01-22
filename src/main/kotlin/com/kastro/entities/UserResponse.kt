package com.kastro.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
)