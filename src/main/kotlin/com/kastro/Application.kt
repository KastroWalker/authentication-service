package com.kastro

import com.kastro.config.initDatabase
import com.kastro.plugins.configureHTTP
import com.kastro.plugins.configureRouting
import com.kastro.plugins.configureSecurity
import com.kastro.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        initDatabase()
        configureSecurity()
        configureHTTP()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
