package com.kastro.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import kotlinx.serialization.Serializable
import java.util.*

class JWTConfig(jwtSecret: String) {
    companion object Constants {
        private const val jwtIssuer = "com.kastro"
        private const val jwtRealm = "com.kastro.authentication-service"

        private const val CLAIM_ID = "id"
        private const val CLAIM_USERNAME = "username"
    }

    private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .build()

    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_ID, user.id)
        .withClaim(CLAIM_USERNAME, user.username)
        .withExpiresAt(Date(System.currentTimeMillis() + 900000))
        .sign(jwtAlgorithm)

    fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(jwtVerifier)
        realm = jwtRealm
        validate {
            val id = it.payload.getClaim(CLAIM_ID).asString()
            val username = it.payload.getClaim(CLAIM_USERNAME).asString()

            if (id != null && username != null) {
                JwtUser(id, username)
            } else {
                null
            }
        }
    }

    @Serializable
    data class JwtUser(val id: String, val username: String): Principal
}