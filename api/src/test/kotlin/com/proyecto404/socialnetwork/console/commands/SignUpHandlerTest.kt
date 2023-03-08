package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.core.application.SignUp
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SignUpHandlerTest {
    @Test
    fun `signup command calls signup use case`() {
        val command = Command("signup", listOf("@alice", "1234"))

        handler.handle(command)

        verify { signUp.exec("@alice", "1234") }
    }

    @Test
    fun `should handler signup command`() {
        assertThat(handler.commandName).isEqualTo("signup")
    }

    private val signUp = mockk<SignUp>(relaxed = true)
    private val handler = SignUpHandler(signUp)
}
