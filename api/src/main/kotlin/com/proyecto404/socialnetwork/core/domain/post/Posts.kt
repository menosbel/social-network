package com.proyecto404.socialnetwork.core.domain.post

import com.proyecto404.socialnetwork.core.domain.user.UserId

interface Posts {
    fun nextId(): PostId
    fun get(id: PostId): Post
    fun findByUserId(userId: UserId): List<Post>
    fun add(post: Post)
}
