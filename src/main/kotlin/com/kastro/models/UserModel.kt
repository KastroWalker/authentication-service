package com.kastro.models

import com.kastro.entities.User
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object UserModel: Table("users") {
    val id: Column<String> = char("id", 36)
    val name: Column<String> = char("name", 200)
    val username: Column<String> = char("username", 50)
    val email: Column<String> = char("email", 200)
    val password: Column<String> = char("password", 60)

    init {
        index(true, email)
        index(true, username)
    }

    fun toUser(row: ResultRow) =
        User(
            id = row[id],
            name = row[name],
            username = row[username],
            email = row[username],
            password = row[password]
        )
}