package com.kastro.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun hashPassword(password: String): String {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray())
}

fun verifyHashPassword(password: String, passwordHashed: String): Boolean {
    val result = BCrypt.verifyer().verify(password.toCharArray(), passwordHashed)
    return result.verified
}