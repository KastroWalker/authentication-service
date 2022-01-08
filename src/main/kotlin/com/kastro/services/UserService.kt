package com.kastro.services

import com.kastro.entities.User
import com.kastro.entities.UserResponse
import com.kastro.repositories.UserRepository
import com.kastro.utils.exceptions.UserNotFound
import com.kastro.utils.hashPassword
import mu.KotlinLogging

class UserService {
    private val repository = UserRepository()
    private val logger = KotlinLogging.logger {}

    fun create(user: User): UserResponse? {
        try {
            user.password = hashPassword(user.password!!)

            val userCreated = repository.create(user)

            if (userCreated) {
                return UserResponse(user.id, user.name, user.username, user.email)
            }

            return null
        } catch (exception: Exception) {
            logger.error { "Error creating user: $exception" }
            throw exception
        }
    }

    fun update(id: String, user: User): UserResponse? {
        try {
            repository.getById(id) ?: throw UserNotFound(id)

            val userUpdated = repository.update(id, user)

            if (userUpdated) {
                return UserResponse(id, user.name, user.username, user.email)
            }

            return null
        } catch (exception: Exception) {
            logger.error { "Error updating user: $exception" }
            throw exception
        }
    }

    fun remove(id: String): Boolean {
        try {
            return repository.remove(id)
        } catch (exception: Exception) {
            logger.error { "Error deleting user: $exception" }
            throw exception
        }
    }
}