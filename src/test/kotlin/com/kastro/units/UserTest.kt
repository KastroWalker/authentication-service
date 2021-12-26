package com.kastro.units

import com.kastro.entities.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotBeEmpty
import org.valiktor.ConstraintViolationException

class UserTest: StringSpec({
    val name = "name test"
    val username = "testuser"
    val email = "test@email.com"
    val password = "passwordtest"

    "Should create an user" {
        val user = User(name, username, email, password)

        user.id.shouldNotBeEmpty()
        user.name.shouldBe(name)
        user.username.shouldBe(username)
        user.email.shouldBe(email)
        user.password.shouldNotBeEmpty()
        user.password.shouldNotBe(password)
    }

    "Should not create with an invalid name" {
        shouldThrow<ConstraintViolationException> {
            User("a", username, email, password)
        }

        val invalidName = "a".repeat(201)
        shouldThrow<ConstraintViolationException> {
            User(invalidName, username, email, password)
        }
    }

    "Should not create with an invalid username" {
        shouldThrow<ConstraintViolationException> {
            User(name, "a", email, password)
        }

        val invalidUsername = "a".repeat(51)
        shouldThrow<ConstraintViolationException> {
            User(name, invalidUsername, email, password)
        }
    }

    "Should not create with an invalid email" {
        shouldThrow<ConstraintViolationException> {
            User(name, username, "a", password)
        }
    }

    "Should not create with an invalid password" {
        shouldThrow<ConstraintViolationException> {
            User(name, username, email, "a")
        }

        val invalidPassword = "a".repeat(51)
        shouldThrow<ConstraintViolationException> {
            User(name, username, email, invalidPassword)
        }
    }
})