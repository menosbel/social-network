package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.Users
import java.time.LocalDateTime

class GetUserPosts(private val posts: Posts, private val users: Users) {
    fun exec(userName: String): Response {
        val userId = users.get(userName)
        return Response(posts.findByUserId(userId.id).map {
            Response.PostData(it.id.toInt(), it.message, it.createdAt)
        })
    }

    data class Response(val posts: List<PostData>) {
        data class PostData(val id: Int, val message: String, val createdAt: LocalDateTime)
    }
}
