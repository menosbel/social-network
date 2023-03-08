package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import com.proyecto404.socialnetwork.core.application.Follow
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FollowHandlerShould {

    @Test
    fun `calls follow use case`() {
        handler.handle(Command("follow", listOf("@bob")))

        verify { follow.exec("@bob", SESSION_TOKEN) }
    }

    @Test
    fun `print error message when no user authenticated`() {
        currentSession.logout()

        val result = handler.handle(Command("follow", listOf("@bob")))

        assertThat(result).isEqualTo("ERROR: User must be logged to follow another user")
    }

    @Test
    fun `print error message when no user to follow is specified`() {
        every { follow.exec(any(), any()) } throws UserNotFoundError()

        val result = handler.handle(Command("follow", listOf()))

        assertThat(result).isEqualTo("ERROR: Must specify an user")
    }

    @BeforeEach
    fun beforeEach() {
        currentSession.authenticate("@alice", SESSION_TOKEN)
    }

    private val currentSession = InMemoryCurrentSession()
    private val follow = mockk<Follow>(relaxed = true)
    private val handler = FollowHandler(follow, currentSession)
    private val SESSION_TOKEN = "asd123"
}