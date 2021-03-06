package com.kastro.entities

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val username: String,
    val email: String,
    var password: String? = null,
)