package com.proyecto404.socialnetwork.core.domain.post

import com.proyecto404.socialnetwork.core.domain.ifBlank
import com.proyecto404.socialnetwork.core.domain.user.UserId
import java.time.LocalDateTime

class Post(val id: PostId, val message: String, val userId: UserId, val createdAt: LocalDateTime) {
    init {
        message.ifBlank { throw InvalidMessageError() }
    }

    override fun equals(other: Any?) = other is Post && other.id == id

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    fun snapshot() = Snapshot(id.toInt(), message, userId.toInt(), createdAt.toString())

    override fun toString() = "Post(id=$id, message='$message', userId='$userId')"

    data class Snapshot(val id: Int, val message: String, val userId: Int, val createdAt: String)

    companion object {
        fun fromSnapshot(snapshot: Snapshot): Post {
            return Post(
                PostId(snapshot.id),
                snapshot.message,
                UserId(snapshot.userId),
                LocalDateTime.parse(snapshot.createdAt)
            )
        }
    }
}
