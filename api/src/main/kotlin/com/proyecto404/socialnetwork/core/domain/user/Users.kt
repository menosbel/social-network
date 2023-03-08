package com.proyecto404.socialnetwork.core.domain.user

interface Users {
    fun nextId(): UserId
    fun get(id: UserId): User
    fun get(userName: String): User
    fun add(user: User)
    fun update(user: User)
    fun findBySessionToken(sessionToken: String): User?
}
