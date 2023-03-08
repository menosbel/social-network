package com.proyecto404.socialnetwork.core.domain.user

class UserId(private val rawId: Int) {
    override fun equals(other: Any?) = other is UserId && other.rawId == rawId

    override fun hashCode() = rawId.hashCode()

    override fun toString() = "UserId($rawId)"

    fun toInt() = rawId
}
