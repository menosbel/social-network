package com.proyecto404.socialnetwork.core.infrastructure.inMemory

import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import com.proyecto404.socialnetwork.core.domain.user.Users

class InMemoryUsers: Users {
    private var nextId = 1
    private val users = mutableListOf<User>()

    override fun nextId() = UserId(nextId++)

    override fun get(id: UserId) = users.singleOrNull { it.id == id } ?: throw UserNotFoundError()

    override fun get(userName: String) = users.singleOrNull { it.userName == userName } ?: throw UserNotFoundError()

    override fun findBySessionToken(sessionToken: String) = users.singleOrNull { it.sessionToken == sessionToken }

    override fun add(user: User) {
        users.add(user)
    }

    override fun update(user: User) {}
}
