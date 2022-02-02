package com.kastro.units.utils

import com.kastro.utils.hashPassword
import com.kastro.utils.verifyHashPassword
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotBeEmpty

class HashPasswordTest: StringSpec({
    "Should encrypt the password" {
        val password = "password"
        val hashPassword = hashPassword(password)

        hashPassword.shouldNotBeEmpty()
        hashPassword.shouldNotBe(password)
    }

    "Should verify a valid password" {
        val password = "password"
        val hashPassword = hashPassword(password)
        val validPassword = verifyHashPassword(password, hashPassword)

        validPassword.shouldBe(true)
    }

    "Should verify an valid password" {
        val password = "password"
        val invalidPassword = "pass"
        val hashPassword = hashPassword(password)
        val validPassword = verifyHashPassword(invalidPassword, hashPassword)

        validPassword.shouldBe(false)
    }
})