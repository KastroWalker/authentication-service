package com.kastro.controllers

import com.kastro.entities.User
import com.kastro.utils.InvalidProperty
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException
import org.valiktor.ConstraintViolationException

fun Route.userRoute() {
    route("/users") {
        createUser()
        getUsers()
    }
}

fun Route.createUser() {
    post {
        try {
            val user = call.receive<User>()
            println(user)
            return@post call.respondText { "Roi" }
        } catch (exception: ConstraintViolationException) {
            return@post call.respondText(
                InvalidProperty(exception).getMessage(), status = HttpStatusCode.UnprocessableEntity
            )
        } catch (exception: SerializationException) {
            return@post call.respondText(
                "You must provide a {name, username, email, password}", status = HttpStatusCode.PaymentRequired
            )
        } catch (exception: Exception) {
            return@post call.respondText("Error creating user", status = HttpStatusCode.InternalServerError)
        }
    }
}

fun Route.getUsers() {
    get {
        return@get call.respondText("All users")
    }
}