package com.proyecto404.socialnetwork.core.infrastructure.inMemory

import com.proyecto404.socialnetwork.core.domain.post.Post
import com.proyecto404.socialnetwork.core.domain.post.PostId
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.UserId

class InMemoryPosts: Posts {
    private var nextId = 1
    private val items = mutableListOf<Post>()

    override fun nextId() = PostId(nextId++)

    override fun get(id: PostId) = items.single { it.id == id }

    override fun findByUserId(userId: UserId) = items.filter { it.userId == userId }

    override fun add(post: Post) {
        items.add(post)
    }
}
