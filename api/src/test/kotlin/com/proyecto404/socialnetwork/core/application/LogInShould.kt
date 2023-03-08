@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.auth.InvalidCredentialsError
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LogInShould {
    @Test
    fun `do login with user credentials`() {
        val alice = createAlice()

        val response = logIn.exec("@alice", "1234")

        assertThat(response.sessionToken).isEqualTo(SESSION_TOKEN)
        assertThat(alice.sessionToken).isEqualTo(SESSION_TOKEN)
    }

    @Test
    fun `fail with invalid password`() {
        createAlice()

        assertThrows<InvalidCredentialsError> {
            logIn.exec("@alice", INVALID_PASSWORD)
        }
    }

    private fun createAlice() = User(UserId(1), "@alice", "1234").also { users.add(it) }

    private val INVALID_PASSWORD = "INVALID"
    private val SESSION_TOKEN = "1234dwsa$%&"
    private val sessionTokenGenerator = SessionTokenGeneratorStub(SESSION_TOKEN)
    private val users = InMemoryUsers()
    private val logIn = LogIn(users, sessionTokenGenerator)
}
