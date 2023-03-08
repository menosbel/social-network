package com.proyecto404.socialnetwork.core.domain.following

import com.proyecto404.socialnetwork.core.domain.user.UserId

class Following(val userId: UserId, val followedId: UserId) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Following

        if (userId != other.userId) return false
        if (followedId != other.followedId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + followedId.hashCode()
        return result
    }

    override fun toString() = "Following(userId=$userId, followedId=$followedId)"

    fun snapshot() = Snapshot(userId.toInt(), followedId.toInt())

    data class Snapshot(val userId: Int, val followedId: Int)

    companion object {
        fun fromSnapshot(snapshot: Snapshot): Following {
            return Following(UserId(snapshot.userId), UserId(snapshot.followedId))
        }
    }
}
