package com.kastro.plugins

import com.kastro.controllers.userRoute
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        userRoute()
    }
}
