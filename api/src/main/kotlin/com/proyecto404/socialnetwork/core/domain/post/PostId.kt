package com.proyecto404.socialnetwork.core.domain.post

class PostId(private val rawId: Int) {
    override fun equals(other: Any?) = other is PostId && other.rawId == rawId

    override fun hashCode() = rawId.hashCode()

    fun toInt() = rawId

    override fun toString() = "PostId($rawId)"
}
