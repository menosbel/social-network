package com.proyecto404.socialnetwork.core.domain.user

import com.proyecto404.socialnetwork.core.domain.auth.SessionTokenGenerator

class User(val id: UserId, val userName: String, val password: String) {
    var sessionToken: String? = null
        private set

    fun createSession(sessionTokenGenerator: SessionTokenGenerator) {
        sessionToken = sessionTokenGenerator.generate()
    }

    fun matchesPassword(password: String) = this.password == password

    override fun equals(other: Any?) = other is User && other.id == id

    override fun hashCode() = id.hashCode()

    override fun toString() = "User($id, $userName)"

    fun snapshot() = Snapshot(id.toInt(), userName, password, sessionToken)

    data class Snapshot(val id: Int, val userName: String, val password: String, val sessionToken: String?)

    companion object {
        fun fromSnapshot(snapshot: Snapshot): User {
            val user = User(UserId(snapshot.id), snapshot.userName, snapshot.password)
            user.sessionToken = snapshot.sessionToken
            return user
        }
    }
}
