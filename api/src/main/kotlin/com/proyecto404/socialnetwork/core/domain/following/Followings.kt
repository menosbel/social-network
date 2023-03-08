package com.proyecto404.socialnetwork.core.domain.following

import com.proyecto404.socialnetwork.core.domain.user.UserId

interface Followings {
    fun findByUser(userId: UserId): List<Following>
    fun add(following: Following)
}
