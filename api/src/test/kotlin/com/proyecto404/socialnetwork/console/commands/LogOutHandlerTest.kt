package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LogOutHandlerTest {
    @Test
    fun `logout command sets anonymous identity`() {
        currentSession.authenticate("@alice")

        handler.handle(Command("logout"))

        assertThat(currentSession.identity.isAuthenticated).isFalse
    }

    @Test
    fun `logout command returns logged out`() {
        currentSession.authenticate("@alice")

        val response = handler.handle(Command("logout"))

        assertThat(response).isEqualTo("Logged out!")
    }

    @Test
    fun `should handle logout command`() {
        assertThat(handler.commandName).isEqualTo("logout")
    }

    private val currentSession = InMemoryCurrentSession()
    private val handler = LogOutHandler(currentSession)
}
