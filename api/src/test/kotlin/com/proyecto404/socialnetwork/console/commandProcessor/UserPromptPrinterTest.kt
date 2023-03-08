package com.proyecto404.socialnetwork.console.commandProcessor

import com.proyecto404.socialnetwork.console.FakeOutput
import com.proyecto404.socialnetwork.console.commandProcessor.prompt.UserPromptPrinter
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserPromptPrinterTest {
    @Test
    fun `prints default prompt on unauthenticated user`() {
        currentSession.logout()

        printer.print(output)

        assertThat(output.content).isEqualTo("> ")
    }

    @Test
    fun `prints current user in command prompt`() {
        currentSession.authenticate("@alice")

        printer.print(output)

        assertThat(output.content).isEqualTo("@alice > ")
    }

    private val currentSession = InMemoryCurrentSession()
    private val printer = UserPromptPrinter(currentSession)
    private val output = FakeOutput()
}
