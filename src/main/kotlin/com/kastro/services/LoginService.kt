package com.kastro.services

import com.kastro.authentication.JWTConfig
import com.kastro.entities.Login
import com.kastro.repositories.UserRepository
import com.kastro.utils.exceptions.UserNotFound
import com.kastro.utils.verifyHashPassword
import io.github.cdimascio.dotenv.dotenv
import mu.KotlinLogging

class LoginService {
    private val dotenv = dotenv()
    private val userRepository = UserRepository()
    private val logger = KotlinLogging.logger { }
    private val jwtConfig = JWTConfig(dotenv["SECRET_KEY"])

    fun login(login: Login): String {
        try {
            val user = userRepository.getByUsername(login.username) ?: throw UserNotFound()

            if (!verifyHashPassword(login.password, user.password!!)) {
                throw UserNotFound()
            }

            return jwtConfig.generateToken(JWTConfig.JwtUser(user.id, user.username))
        } catch (exception: Exception) {
            logger.error { "Error fetching user: $exception" }
            throw exception
        }
    }
}