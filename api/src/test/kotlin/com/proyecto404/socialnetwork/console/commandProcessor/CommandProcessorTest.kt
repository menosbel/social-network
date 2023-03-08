package com.proyecto404.socialnetwork.console.commandProcessor

import com.proyecto404.socialnetwork.console.FakeInput
import com.proyecto404.socialnetwork.console.FakeOutput
import com.proyecto404.socialnetwork.console.commandProcessor.prompt.SimplePromptPrinter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CommandProcessorTest {
    @Test
    fun `process a command`() {
        input.willReadAndExit("some-command")

        console().run()

        assertThat(output.lines().take(2)).containsExactly(
            "> some-command",
            "some command response",
        )
    }

    @Test
    fun `invalid command outputs error message`() {
        input.willReadAndExit("asdasd")

        console().run()

        assertThat(output.lines().take(2)).containsExactly(
            "> asdasd",
            "ERROR: invalid command asdasd",
        )
    }

    @Test
    fun `process multiple commands`() {
        input.willReadAndExit("some-command", "other-command")

        console().run()

        assertThat(output.lines().take(4)).containsExactly(
            "> some-command",
            "some command response",
            "> other-command",
            "some other command response",
        )
    }

    @Test
    fun `exit command closes app`() {
        input.willRead("exit")

        console().run()

        assertThat(output.lines()).containsExactly(
            "> exit",
            "bye bye"
        )
    }

    private fun FakeInput.willReadAndExit(vararg line: String) = this.willRead(*line).also { this.willRead("exit") }

    private fun FakeOutput.lines() = this.content.lines().dropLast(1)

    private fun console(): CommandProcessor {
        val handlerFactory = CommandHandlerFactory().apply {
            register(SomeCommandHandler())
            register(SomeOtherCommandHandler())
        }
        val promptPrinter = SimplePromptPrinter("> ")
        return CommandProcessor(input, output, handlerFactory, promptPrinter)
    }

    private val output = FakeOutput()
    private val input = FakeInput(output)

    class SomeCommandHandler : CommandHandler {
        override val commandName = "some-command"
        override fun handle(command: Command) = "some command response"
    }

    class SomeOtherCommandHandler : CommandHandler {
        override val commandName = "other-command"
        override fun handle(command: Command) = "some other command response"
    }
}

