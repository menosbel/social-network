package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.Clock
import com.proyecto404.socialnetwork.core.domain.auth.UnauthenticatedError
import com.proyecto404.socialnetwork.core.domain.post.Post
import com.proyecto404.socialnetwork.core.domain.post.PostId
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.Users

class CreatePost(private val posts: Posts, private val users: Users, private val clock: Clock) {
    fun exec(message: String, sessionToken: String): PostId {
        val user = users.findBySessionToken(sessionToken) ?: throw UnauthenticatedError()
        val id = posts.nextId()
        val post = Post(id, message, user.id, clock.now())
        posts.add(post)
        return id
    }
}
