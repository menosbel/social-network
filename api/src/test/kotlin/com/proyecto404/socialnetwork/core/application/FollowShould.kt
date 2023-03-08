package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.following.AlreadyFollowingError
import com.proyecto404.socialnetwork.core.domain.following.Following
import com.proyecto404.socialnetwork.core.domain.auth.UnauthenticatedError
import com.proyecto404.socialnetwork.core.domain.user
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryFollowings
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FollowShould {
    @Test
    fun `follow given user`() {
        follow.exec(bob.userName, ALICE_SESSION_TOKEN)

        val aliceFollowings = followings.findByUser(alice.id)
        assertThat(aliceFollowings).contains(Following(alice.id, bob.id))
    }

    @Test
    fun `fail when followed doesn't exist`() {
        assertThrows<UserNotFoundError> { follow.exec("@InvalidUser", ALICE_SESSION_TOKEN) }
    }

    @Test
    fun `fail when session token is invalid `() {
        assertThrows<UnauthenticatedError> { follow.exec(bob.userName, INVALID_SESSION_TOKEN) }
    }

    @Test
    fun `fail when following relationship already exists`() {
        follow.exec(bob.userName, ALICE_SESSION_TOKEN)

        assertThrows<AlreadyFollowingError> { follow.exec(bob.userName, ALICE_SESSION_TOKEN) }
    }

    private val INVALID_SESSION_TOKEN = "INVALID SESSION TOKEN"
    private val ALICE_SESSION_TOKEN = "ajskk123"
    private val followings = InMemoryFollowings()
    private val users = InMemoryUsers()
    private val follow = Follow(followings, users)
    private val alice = user { authenticated(ALICE_SESSION_TOKEN) }.also { users.add(it) }
    private val bob = user().also { users.add(it) }
}
