package com.kastro.utils

import org.jetbrains.exposed.exceptions.ExposedSQLException

import java.util.regex.*;

class DuplicatedProperty (private val exception: ExposedSQLException) : Exception() {
    var invalidProperties = "mutableListOf<String>()"

    init {
        val message = exception.message.toString()
        val regex = "Key \\((.*?)\\)="
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)

        while (matcher.find())
        {
            this.invalidProperties = matcher.group(1).split(",").toMutableList()[0]
        }
    }
}