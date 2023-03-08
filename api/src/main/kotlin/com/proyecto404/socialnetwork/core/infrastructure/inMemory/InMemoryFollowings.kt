package com.proyecto404.socialnetwork.core.infrastructure.inMemory

import com.proyecto404.socialnetwork.core.domain.following.AlreadyFollowingError
import com.proyecto404.socialnetwork.core.domain.following.Following
import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.user.UserId

class InMemoryFollowings: Followings {
    private val followings = mutableListOf<Following>()

    override fun findByUser(userId: UserId) = followings.filter{ it.userId == userId }

    override fun add(following: Following) {
        if (followings.contains(following)) throw AlreadyFollowingError()
        followings.add(following)
    }
}
