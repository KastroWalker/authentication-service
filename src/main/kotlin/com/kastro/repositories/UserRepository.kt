package com.kastro.repositories

import com.kastro.config.initDatabase
import com.kastro.entities.User
import com.kastro.models.UserModel
import com.kastro.utils.DuplicatedProperty
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

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
                    it[password] = user.password
                }
            }

            return insertion.insertedCount == 1
        } catch (exception: ExposedSQLException) {
            val isUniqueConstraintError = exception.sqlState == "23505"

            if (isUniqueConstraintError) {
                throw DuplicatedProperty(exception)
            }

            return false
        } catch (exception: Exception) {
            logger.error { "Error saving user: $exception" }
            return false
        }
    }
}

//fun main() {
//    try {
//        initDatabase()
//        val repository = UserRepository()
//        val newUser = User(name = "victor", username = "victor_castro2", email =  "victorcsa20022@gmail.com", password = "senha123")
//        print(repository.create(newUser))
//    } catch (exception: DuplicatedProperty) {
//        println(exception.invalidProperties)
//    }
//}