@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.console.formatters.StoppedClock
import com.proyecto404.socialnetwork.core.domain.PostExamples.messages
import com.proyecto404.socialnetwork.core.domain.auth.UnauthenticatedError
import com.proyecto404.socialnetwork.core.domain.post.InvalidMessageError
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryPosts
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

@Suppress("LocalVariableName")
class CreatePostShould {
    @Test
    fun `creates a new post`() {
        val createdId = createPost.exec("Hola Mundo", ALICE_SESSION)

        val post = posts.get(createdId)
        assertThat(post.id).isEqualTo(createdId)
        assertThat(post.message).isEqualTo("Hola Mundo")
        assertThat(post.userId).isEqualTo(ALICE_ID)
        assertThat(post.createdAt).isEqualTo(NOW)
    }

    @Test
    fun `fail when session token is invalid`() {
        assertThrows<UnauthenticatedError> {
            createPost.exec(messages.one(), INVALID_SESSION_TOKEN)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `fail when message is invalid`(invalidMessage: String) {
        assertThrows<InvalidMessageError> {
            createPost.exec(invalidMessage, ALICE_SESSION)
        }
    }

    @BeforeEach
    fun beforeEach() {
        val alice = User(ALICE_ID, "@alice", "1234")
        alice.createSession(SessionTokenGeneratorStub(ALICE_SESSION))
        users.add(alice)
    }

    private val INVALID_SESSION_TOKEN = "!#&#$"
    private val ALICE_ID = UserId(57)
    private val ALICE_SESSION = "asdf1234"
    private val NOW = LocalDateTime.of(2021, 8, 18, 17, 35, 15)
    private val clock = StoppedClock.at(NOW)
    private val posts = InMemoryPosts()
    private val users = InMemoryUsers()
    private val createPost = CreatePost(posts, users, clock)
}
