package com.kastro.strategies

import com.kastro.entities.User
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotEmpty
import org.valiktor.validate

object UserRequestUpdate : UserRequestStrategy {
    override fun validate(user: User) {
        try {
            validate(user) {
                validate(User::name).isNotEmpty().hasSize(3, 200)
                validate(User::username).isNotEmpty().hasSize(3, 50)
                validate(User::email).isNotEmpty().isEmail()
            }
        } catch (exception: ConstraintViolationException) {
            throw exception
        }
    }
}