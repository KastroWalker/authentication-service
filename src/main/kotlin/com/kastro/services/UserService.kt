package com.kastro.services

import com.kastro.entities.User
import com.kastro.repositories.UserRepository
import kotlinx.serialization.Serializable
import mu.KotlinLogging

@Serializable
data class UserReponse (
    val id: String,
    val name: String,
    val username: String,
    val email: String,
)

class UserService {
    private val repository = UserRepository()
    private val logger = KotlinLogging.logger {}

    fun create(user: User): UserReponse? {
        try {
            val userCreated = repository.create(user)

            if (userCreated) {
                return UserReponse(user.id, user.name, user.username, user.email)
            }

            return null
        } catch (exception: Exception) {
            logger.error { "Error creating user: $exception" }
            throw exception
        }
    }
}