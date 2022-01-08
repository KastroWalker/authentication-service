package com.kastro.controllers

import com.kastro.entities.User
import com.kastro.services.UserService
import com.kastro.strategies.UserRequestInsert
import com.kastro.utils.DuplicatedProperty
import com.kastro.utils.InvalidProperty
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException
import mu.KotlinLogging
import org.valiktor.ConstraintViolationException

private val service = UserService()
private val logger = KotlinLogging.logger {}

fun Route.userRoute() {
    route("/users") {
        createUser()
        updateUser()
        getUsers()
    }
}

fun Route.createUser() {
    post {
        try {
            val user = call.receive<User>()

            UserRequestInsert.validate(user)

            val response = service.create(user)

            logger.info { "New User created: $response" }

            if (response != null) {
                return@post call.respond(status = HttpStatusCode.Created, response)
            }

            throw Exception()
        } catch (exception: ConstraintViolationException) {
            logger.error { "Error creating user: $exception" }
            val exceptionValues = exception.constraintViolations.first()
            if (exceptionValues.constraint.name == "NotNull" && exceptionValues.property == "password") {
                return@post call.respondText(
                "You must provide a {name, username, email, password}", status = HttpStatusCode.PaymentRequired
                )
            }
            return@post call.respondText(
                InvalidProperty(exception).getMessage(), status = HttpStatusCode.UnprocessableEntity
            )
        } catch (exception: SerializationException) {
            logger.error { "Error creating user: $exception" }
            return@post call.respondText(
                "You must provide a {name, username, email, password}", status = HttpStatusCode.PaymentRequired
            )
        } catch (exception: DuplicatedProperty) {
            logger.error { "Error creating user: $exception" }
            return@post call.respondText(
                "The [${exception.invalidProperty}] already exists", status = HttpStatusCode.UnprocessableEntity
            )
        } catch (exception: Exception) {
            logger.error { "Error creating user: $exception" }
            return@post call.respondText("Error creating user", status = HttpStatusCode.InternalServerError)
        }
    }
}

fun Route.updateUser() {
    put {
        try {
            // @TODO: Get user id from Token

            val user = call.receive<User>()

            print(user)
            return@put call.respondText("HELLO")
        } catch (exception: Exception) {
            logger.error { "Error updating user: $exception" }
        }
    }
}

fun Route.getUsers() {
    get {
        return@get call.respondText("All users")
    }
}