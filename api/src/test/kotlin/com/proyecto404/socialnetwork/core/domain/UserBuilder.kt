package com.proyecto404.socialnetwork.core.domain

import com.proyecto404.socialnetwork.core.application.SessionTokenGeneratorStub
import com.proyecto404.socialnetwork.core.domain.UserExamples.usernames
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId

private var nextId = 1

class UserBuilder {
    private var id = UserId(nextId++)
    private var userName = usernames.one()
    private var password = "1234"
    private var sessionToken: String? = null

    fun id(value: Int): UserBuilder {
        id = UserId(value)
        return this
    }

    fun userName(value: String): UserBuilder {
        userName = value
        return this
    }

    fun authenticated(sessionToken: String): UserBuilder {
        this.sessionToken = sessionToken
        return this
    }

    fun password(value: String): UserBuilder {
        password = value
        return this
    }

    fun build(): User {
        val user = User(id, userName, password)
        if(sessionToken != null) user.createSession(SessionTokenGeneratorStub(sessionToken!!))
        return user
    }
}

fun user(addDetails: UserBuilder.() -> Unit = {}) = UserBuilder().also(addDetails).build()
