package com.kastro.units.strategies

import com.kastro.entities.User
import com.kastro.strategies.UserRequestInsert
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import org.valiktor.ConstraintViolationException

class UserRequestInsertTest : StringSpec({
    val name = "name test"
    val username = "testuser"
    val email = "test@email.com"
    val password = "passwordtest"

    "Should validate an user" {
        val user = User(
            name = name,
            username = username,
            email = email,
            password = password
        )

        shouldNotThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }
    }

    "Should validate name" {
        var user = User(name = "te", username = username, email = email, password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }

        user = User(name = "teste".repeat(201), username = username, email = email, password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }
    }

    "Should validate username" {
        var user = User(name = name, username = "te", email = email, password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }

        user = User(name = name, username = "teste".repeat(50), email = email, password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }
    }

    "Should validate email" {
        var user = User(name = name, username = username, email = "invalid_email", password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }

        user = User(name = name, username = username, email = "invalid_email@hotmail", password = password)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }
    }

    "Should validate password" {
        var user = User(name = name, username = username, email = email, password = "te")

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }

        user = User(name = name, username = username, email = email, password = "te".repeat(51))

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }

        user = User(name = name, username = username, email = email, password = null)

        shouldThrow<ConstraintViolationException> {
            UserRequestInsert.validate(user)
        }
    }
})
