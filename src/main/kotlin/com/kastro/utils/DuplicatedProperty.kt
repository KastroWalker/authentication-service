package com.kastro.utils

import org.jetbrains.exposed.exceptions.ExposedSQLException

import java.util.regex.*;

class DuplicatedProperty (exception: ExposedSQLException) : Exception() {
    var invalidProperty = ""

    init {
        val message = exception.message.toString()
        val regex = "Key \\((.*?)\\)="
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)

        while (matcher.find())
        {
            this.invalidProperty = matcher.group(1).split(",").toMutableList()[0]
        }
    }
}