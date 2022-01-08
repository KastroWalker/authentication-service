package com.kastro.strategies

import com.kastro.entities.User

interface UserRequestStrategy {
    fun validate(user: User)
}