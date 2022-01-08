package com.kastro.controllers

import com.kastro.entities.User
import com.kastro.services.UserService
import com.kastro.strategies.UserRequestInsert
import com.kastro.strategies.UserRequestUpdate
import com.kastro.utils.InvalidProperty
import com.kastro.utils.exceptions.DuplicatedProperty
import com.kastro.utils.exceptions.UserNotFound
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
        removeUser()
    }
}

fun Route.createUser() {
    post {
        try {
            val user = call.receive<User>()
            UserRequestInsert.validate(user)
            val response = service.create(user)
            if (response != null) {
                logger.info { "New User created: $response" }
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
            val id = call.parameters["id"] ?: return@put call.respondText(
                "An id is required",
                status = HttpStatusCode.PaymentRequired
            )
            val user = call.receive<User>()
            UserRequestUpdate.validate(user)
            val response = service.update(id, user)
            if (response != null) {
                logger.info { "User updated: $response" }
                return@put call.respond(status = HttpStatusCode.OK, response)
            }
            throw Exception()
        } catch (exception: ConstraintViolationException) {
            logger.error { "Error updating user: $exception" }
            return@put call.respondText(
                InvalidProperty(exception).getMessage(), status = HttpStatusCode.UnprocessableEntity
            )
        } catch (exception: SerializationException) {
            logger.error { "Error updating user: $exception" }
            return@put call.respondText(
                "You must provide a {name, username, email}", status = HttpStatusCode.PaymentRequired
            )
        } catch (exception: DuplicatedProperty) {
            logger.error { "Error updating user: $exception" }
            return@put call.respondText(
                "The [${exception.invalidProperty}] already exists", status = HttpStatusCode.UnprocessableEntity
            )
        } catch (exception: UserNotFound) {
            logger.error { "Error updating user: $exception" }
            return@put call.respondText(
                "The User with id ${exception.id} not exist",
                status = HttpStatusCode.NotFound
            )
        } catch (exception: Exception) {
            logger.error { "Error updating user: $exception" }
            return@put call.respondText("Error updating user", status = HttpStatusCode.InternalServerError)
        }
    }
}

fun Route.removeUser() {
    delete {
        try {
            // @TODO: Get user id from Token
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "An id is required",
                status = HttpStatusCode.PaymentRequired
            )
            val response = service.remove(id)
            if (response) {
                return@delete call.respondText(
                    "User removed",
                    status = HttpStatusCode.NoContent
                )
            }
            throw Exception()
        } catch (exception: UserNotFound) {
            logger.error { "Error deleting user: $exception" }
            return@delete call.respondText(
                "The User with id ${exception.id} not exist",
                status = HttpStatusCode.NotFound
            )
        } catch (exception: Exception) {
            logger.error { "Error deleting user: $exception" }
            return@delete call.respondText("Error deleting user", status = HttpStatusCode.InternalServerError)
        }
    }
}