package com.proyecto404.socialnetwork.core.domain

import com.proyecto404.socialnetwork.core.domain.PostExamples.messages
import com.proyecto404.socialnetwork.core.domain.post.Post
import com.proyecto404.socialnetwork.core.domain.post.PostId
import com.proyecto404.socialnetwork.core.domain.user.User
import java.time.LocalDateTime

private var nextId = 1

class PostBuilder {
    private var id = PostId(nextId++)
    private var message = messages.one()
    private var userId = UserBuilder().build().id
    private val createdAt = LocalDateTime.now()

    fun id(value: Int): PostBuilder {
        id = PostId(value)
        return this
    }

    fun creator(value: User): PostBuilder {
        userId = value.id
        return this
    }

    fun message(value: String): PostBuilder {
        message = value
        return this
    }

    fun user(addDetails: UserBuilder.() -> Unit): User {
        val user = UserBuilder().also(addDetails).build()
        userId = user.id
        return user
    }

    fun build() = Post(id, message, userId, createdAt)
}

fun post(addDetails: PostBuilder.() -> Unit = {}) = PostBuilder().also(addDetails).build()
