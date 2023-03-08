package com.proyecto404.socialnetwork.core.infrastructure

import com.proyecto404.socialnetwork.core.application.*
import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.Users

class UseCaseProvider(private val config: CoreConfiguration ) {
    private val repositories = config.repositories.cached()

    fun signUp() = SignUp(repositories.create())

    fun logIn() = LogIn(repositories.create(), config.sessionTokenGenerator)

    fun createPost() = CreatePost(repositories.create(), repositories.create(), config.clock)

    fun getUserPosts() = GetUserPosts(repositories.create(), repositories.create())

    fun follow() = Follow(repositories.create(), repositories.create())
}
