package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.application.GetUserPosts.Response.PostData
import com.proyecto404.socialnetwork.core.domain.post
import com.proyecto404.socialnetwork.core.domain.post.Post
import com.proyecto404.socialnetwork.core.domain.user
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryPosts
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetUserPostsShould {
    @Test
    fun `return user's posts`() {
        val user = user().also { users.add(it) }
        val post1 = post { creator(user) }.also { posts.add(it) }
        val post2 = post { creator(user) }.also { posts.add(it) }

        val response = getUserPosts.exec(user.userName)

        assertThat(response.posts).containsExactlyInAnyOrder(postData(post1), postData(post2))
    }

    @Test
    fun `return error when user doesn't exists`() {
        val invalidUserName = "@"

        assertThrows<UserNotFoundError> {
            getUserPosts.exec(invalidUserName)
        }
    }

    private fun postData(post: Post): PostData {
        return PostData(post.id.toInt(), post.message, post.createdAt)
    }

    private val posts = InMemoryPosts()
    private val users = InMemoryUsers()
    private val getUserPosts = GetUserPosts(posts, users)
}
