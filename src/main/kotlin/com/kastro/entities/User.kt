package com.kastro.entities

import com.kastro.utils.hashPassword
import kotlinx.serialization.Serializable
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotEmpty
import org.valiktor.validate
import java.util.*

@Serializable
data class User(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    var password: String,
) {
    constructor(name: String, username: String, email: String, password: String) : this(
        UUID.randomUUID().toString(),
        name,
        username,
        email,
        password
    ) {
        validate(this) {
            validate(User::name).isNotEmpty().hasSize(3, 200)
            validate(User::username).isNotEmpty().hasSize(3, 50)
            validate(User::email).isNotEmpty().isEmail()
            validate(User::password).isNotEmpty().hasSize(8, 50)
        }

        this.password = hashPassword(password)
    }
}