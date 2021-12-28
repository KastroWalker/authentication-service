package com.kastro.config

import com.kastro.models.UserModel
import io.github.cdimascio.dotenv.dotenv
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException

val dotenv = dotenv()
val logger = KotlinLogging.logger {}

fun initDatabase() {
    try {
        Database.connect(
            "jdbc:postgresql://${dotenv["DB_HOST"]}:5432/${dotenv["DB_NAME"]}",
            driver = "org.postgresql.Driver",
            user = dotenv["DB_USER"],
            password = dotenv["DB_PASS"]
        )
        transaction {
            SchemaUtils.create(UserModel)
        }
        logger.info { "Database connected" }
    } catch (error: PSQLException) {
        logger.error { "Database connection error: $error" }
    }
}