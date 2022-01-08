package com.kastro.repositories

import com.kastro.config.initDatabase
import com.kastro.entities.User
import com.kastro.models.UserModel
import com.kastro.utils.DuplicatedProperty
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserRepository {
    private val logger = KotlinLogging.logger {}

    fun create(user: User): Boolean {
        try {
            val insertion = transaction {
                UserModel.insert {
                    it[id] = user.id
                    it[name] = user.name
                    it[username] = user.username
                    it[email] = user.email
                    it[password] = user.password!!
                }
            }

            return insertion.insertedCount == 1
        } catch (exception: ExposedSQLException) {
            logger.error { "Error saving user during creation: $exception" }
            val isUniqueConstraintError = exception.sqlState == "23505"

            if (isUniqueConstraintError) {
                throw DuplicatedProperty(exception)
            }

            throw exception
        } catch (exception: Exception) {
            logger.error { "Error saving user during creation: $exception" }
            throw exception
        }
    }

    fun update(id: String, user: User): Boolean {
        try {
            val edition = transaction {
                UserModel.update({UserModel.id eq id}) {
                    it[name] = user.name
                    it[username] = user.username
                    it[email] = user.email
                }
            }

            return edition == 1
        } catch (exception: ExposedSQLException) {
            logger.error { "Error saving user during update: $exception" }
            throw exception
        } catch (exception: Exception) {
            logger.error { "Error saving user during update: $exception" }
            throw exception
        }
    }
}

fun main() {
    initDatabase()
    val user = User(
        name = "nametest",
        username = "usernametest",
        email = "emailtest@email.com",
        password = null
    )
//    val userUpdated = UserRepository().update("38e55ca0-aa39-40af-b9b8-8dfdcc7ffa28", );
}
