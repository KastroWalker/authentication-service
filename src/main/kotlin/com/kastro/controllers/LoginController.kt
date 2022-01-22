package com.kastro.controllers

import com.kastro.entities.Login
import com.kastro.services.LoginService
import com.kastro.utils.exceptions.UserNotFound
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private val service = LoginService()

fun Route.loginRoute() {
    route("/login") {
        post {
            try {
                val login = call.receive<Login>()
                val token = service.login(login)
                return@post call.respond(token)
            } catch (exception: UserNotFound) {
                logger.error { "Error when logging in: $exception" }
                return@post call.respondText("Invalid credentials", status = HttpStatusCode.Unauthorized)
            } catch (exception: Exception) {
                logger.error { "Error when logging in: $exception" }
                return@post call.respondText("Error trying to login", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}