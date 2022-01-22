package com.kastro.plugins

import com.kastro.controllers.loginRoute
import com.kastro.controllers.userRoute
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        userRoute()
        loginRoute()
    }
}
