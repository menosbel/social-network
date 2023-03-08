package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SignUpShould {
    @Test
    fun `create username and password`() {
        val createdId = signUp.exec("@alice", "1234")

        val newUser = users.get(createdId)
        assertThat(newUser.userName).isEqualTo("@alice")
        assertThat(newUser.password).isEqualTo("1234")
    }

    private val users = InMemoryUsers()
    private val signUp = SignUp(users)
}
