package com.kastro.repositories

import com.kastro.entities.User
import com.kastro.models.UserModel
import com.kastro.utils.exceptions.DuplicatedProperty
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
            val isUniqueConstraintError = exception.sqlState == "23505"

            if (isUniqueConstraintError) {
                throw DuplicatedProperty(exception)
            }

            throw exception
        } catch (exception: Exception) {
            logger.error { "Error saving user during update: $exception" }
            throw exception
        }
    }

    fun getUserById(id: String): User? {
        try {
            val user = transaction {
                UserModel.select {UserModel.id eq id}.firstOrNull()
            } ?: return null

            return UserModel.toUser(user)
        } catch (exception: Exception) {
            logger.error { "Error fetching user: $exception" }
            throw exception
        }
    }
}
