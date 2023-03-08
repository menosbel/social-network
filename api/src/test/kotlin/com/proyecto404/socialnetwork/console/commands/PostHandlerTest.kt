@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import com.proyecto404.socialnetwork.core.application.CreatePost
import com.proyecto404.socialnetwork.core.domain.post.InvalidMessageError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PostHandlerTest {
    @Test
    fun `calls post use case with given message`() {
        handler.handle(Command("post", listOf("Hola", "Mundo")))

        verify { post.exec("Hola Mundo", SESSION_TOKEN) }
    }

    @Test
    fun `print error message when no user authenticated`() {
        currentSession.logout()

        val result = handler.handle(Command("post", listOf("Hola", "Mundo")))

        assertThat(result).isEqualTo("ERROR: User must be logged to post messages")
    }

    @Test
    fun `print error message when post message is empty`() {
        every { post.exec(any(), any()) } throws InvalidMessageError()

        val result = handler.handle(Command("post", listOf("")))

        assertThat(result).isEqualTo("ERROR: Empty message")
    }

    @BeforeEach
    fun beforeEach() {
        currentSession.authenticate("@alice", SESSION_TOKEN)
    }

    private val SESSION_TOKEN = "asdf1234"
    private val currentSession = InMemoryCurrentSession()
    private val post = mockk<CreatePost>(relaxed = true)
    private val handler = PostHandler(post, currentSession)
}
