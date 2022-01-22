package com.kastro.utils

import org.valiktor.ConstraintViolationException

class InvalidProperty(
    private val exception: ConstraintViolationException
) {
    fun getMessage(): String {
        val property = this.exception.constraintViolations.first().property
        val value = this.exception.constraintViolations.first().value
        return "'$value' is an invalid $property"
    }
}