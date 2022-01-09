package com.kastro.entities

import kotlinx.serialization.Serializable
import org.valiktor.functions.isNotEmpty
import org.valiktor.validate

@Serializable
data class Login(
    val username: String,
    var password: String,
) {
    init {
        validate(this) {
            validate(Login::username).isNotEmpty()
            validate(Login::password).isNotEmpty()
        }
    }
}