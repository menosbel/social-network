package com.proyecto404.socialnetwork.console.e2e

import com.proyecto404.socialnetwork.console.Application
import com.proyecto404.socialnetwork.console.ApplicationConfiguration
import com.proyecto404.socialnetwork.console.FakeInput
import com.proyecto404.socialnetwork.console.FakeOutput
import com.proyecto404.socialnetwork.core.infrastructure.CoreConfiguration
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryRepositoryFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ApplicationEnd2EndTests {
    @Test
    fun login() {
        input.willRead("signup @alice 1234", "login @alice 1234", "exit")

        application().run()

        assertThat(output.content.lines()).containsExactly(
            "> signup @alice 1234",
            "> login @alice 1234",
            "Logged in as @alice!",
            "@alice > exit",
            "bye bye",
            ""
        )
    }

    @Test
    fun post() {
        loginAsAlice()
        input.willRead("post Hola Mundo")
        input.willReadAndExit("read @alice")

        application().run()

        assertThat(output.content.lines()).contains(
            "@alice > post Hola Mundo",
            "@alice > read @alice",
            "- Hola Mundo (just now)",
        )
    }

    @Test
    @Disabled
    fun follow() {
        createUsers("@bob", "@pepe")
        loginAsAlice()
        input.willReadAndExit("follow @bob", "follow @pepe", "following")

        application().run()

        assertThat(output.content.lines()).contains(
            "@alice > follow @bob",
            "@alice > follow @pepe",
            "@alice > following",
            "- @bob",
            "- @pepe",
        )
    }

    private fun createUsers(vararg user: String) {
        input.willRead(*user.map { "signup $it 1234" }.toTypedArray())
    }

    private fun loginAsAlice() {
        input.willRead("signup @alice 1234", "login @alice 1234")
    }

    private fun FakeInput.willReadAndExit(vararg line: String) = this.willRead(*line).also { this.willRead("exit") }

    private fun application(): Application {
        val repositories = InMemoryRepositoryFactory()
        val config = ApplicationConfiguration(UseCaseProvider(CoreConfiguration(repositories = repositories)), input = input, output = output)
        return Application(config)
    }

    private val output = FakeOutput()
    private val input = FakeInput(output)
}
