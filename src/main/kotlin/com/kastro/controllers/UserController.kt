package com.kastro.controllers

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute() {
    route("/users") {
        getUsers()
    }
}

fun Route.getUsers() {
    get {
        return@get call.respondText("All users")
    }
}