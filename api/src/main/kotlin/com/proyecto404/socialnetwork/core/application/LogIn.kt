package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.auth.InvalidCredentialsError
import com.proyecto404.socialnetwork.core.domain.auth.SessionTokenGenerator
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.Users

class LogIn(private val users: Users, private val sessionTokenGenerator: SessionTokenGenerator) {
    fun exec(username: String, password: String): Response {
        val user = users.get(username)
        failIfWrongPassword(user, password)
        user.createSession(sessionTokenGenerator)
        users.update(user)
        return Response(user.sessionToken!!)
    }

    private fun failIfWrongPassword(user: User, password: String) {
        if (!user.matchesPassword(password)) throw InvalidCredentialsError()
    }

    data class Response(val sessionToken: String)
}
