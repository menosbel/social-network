@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.formatters.StoppedClock
import com.proyecto404.socialnetwork.core.application.GetUserPosts
import com.proyecto404.socialnetwork.core.application.GetUserPosts.Response
import com.proyecto404.socialnetwork.core.application.GetUserPosts.Response.PostData
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReadHandlerTest {
    @Test
    fun `read command calls read use case`() {
        handler.handle(Command("read", listOf("@alice")))

        verify { getUserPosts.exec("@alice") }
    }


    @Test
    fun `read command returns and format required user post messages`() {
        every { getUserPosts.exec(any()) } returns Response(listOf(
            post("Hola mundo", now),
            post("Chau mundo", now.minusHours(2))
        ))

        val output = handler.handle(Command("read", listOf("@alice")))

        assertThat(output?.lines()).containsExactly(
            "- Hola mundo (just now)",
            "- Chau mundo (2 hours ago)"
        )
    }

    @Test
    fun `returns error message when userName doesn't exist`() {
        every { getUserPosts.exec(any()) } throws UserNotFoundError()
        val unknownUserName = "@invalid"

        val result = handler.handle(Command("read", listOf(unknownUserName)))

        assertThat(result).isEqualTo("ERROR: Unknown user $unknownUserName")
    }

    @Test
    fun `returns error message when userName is empty`() {
        val emptyUserName = ""

        val result = handler.handle(Command("read", listOf(emptyUserName)))

        assertThat(result).isEqualTo("ERROR: Must specify a user")
    }

    private fun post(message: String, createdAt: LocalDateTime): PostData {
        return PostData(1, message, createdAt)
    }

    private val now = LocalDateTime.of(2021, 2, 25, 17, 0, 0)
    private val getUserPosts = mockk<GetUserPosts>(relaxed = true)
    private val handler = ReadHandler(getUserPosts, StoppedClock.at(now))
}
