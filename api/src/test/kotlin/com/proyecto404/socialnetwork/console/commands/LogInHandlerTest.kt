@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import com.proyecto404.socialnetwork.console.session.auth.UserIdentity
import com.proyecto404.socialnetwork.core.application.LogIn
import com.proyecto404.socialnetwork.core.domain.auth.InvalidCredentialsError
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LogInHandlerTest {

    @Test
    fun `calls login use case`() {
        handler.handle(loginAsAliceCommand())

        verify { logIn.exec("@alice", "1234") }
    }

    @Test
    fun `returns logged in message on success`() {
        val response = handler.handle(loginAsAliceCommand())

        assertThat(response).isEqualTo("Logged in as @alice!")
    }

    @Test
    fun `returns error message with invalid credentials`() {
        every { logIn.exec(any(), any()) } throws InvalidCredentialsError()

        val response = handler.handle(loginAsAliceCommand(password = INVALID_PASSWORD))

        assertThat(response).isEqualTo("ERROR: Invalid credentials for @alice")
    }
    
    @Test
    fun `returns error message with invalid username`() {
        every { logIn.exec(any(), any()) } throws UserNotFoundError()
        val INVALID_USERNAME = "@aliceee"

        val response = handler.handle(loginAsAliceCommand(userName = INVALID_USERNAME))

        assertThat(response).isEqualTo("ERROR: Invalid username")
    }

    @Test
    fun `save userName in current session`() {
        handler.handle(loginAsAliceCommand())

        assertThat((currentSession.identity as UserIdentity).userName).isEqualTo("@alice")
    }

    @Test
    fun `handle login command`() {
        assertThat(handler.commandName).isEqualTo("login")
    }

    @Test
    fun `save session token in current session`() {
        every { logIn.exec(any(), any()) } returns LogIn.Response(SESSION_TOKEN)

        handler.handle(loginAsAliceCommand())

        assertThat((currentSession.identity as UserIdentity).sessionToken).isEqualTo(SESSION_TOKEN)
    }

    private fun loginAsAliceCommand(password: String = "1234", userName: String = "@alice") = Command("login", listOf(userName, password))

    private val logIn = mockk<LogIn>(relaxed = true)
    private val currentSession = InMemoryCurrentSession()
    private val handler = LogInHandler(logIn, currentSession)
    private val INVALID_PASSWORD = "INVALID"
    private val SESSION_TOKEN = "1234dwsa$%&"
}
