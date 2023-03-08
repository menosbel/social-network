package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.domain.user.Users

class SignUp(private val users: Users) {
    fun exec(username: String, password: String): UserId {
        val id = users.nextId()
        val newUser = User(id, username, password)
        users.add(newUser)
        return id
    }
}
