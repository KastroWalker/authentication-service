package com.kastro.plugins

import com.kastro.authentication.JWTConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

val jwtConfig = JWTConfig("351ce5e375dad5b5507f0316e2f68ad2")

fun Application.configureSecurity() {
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
}