package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.following.Following
import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.auth.UnauthenticatedError
import com.proyecto404.socialnetwork.core.domain.user.Users

class Follow(private val followings: Followings, private val users: Users) {
    fun exec(followedName: String, sessionToken: String): String {
        val user = users.findBySessionToken(sessionToken) ?: throw UnauthenticatedError()
        val followed = users.get(followedName)
        val following = Following(user.id, followed.id)
        followings.add(following)
        return ""
    }
}
