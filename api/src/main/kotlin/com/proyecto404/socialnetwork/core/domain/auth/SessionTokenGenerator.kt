package com.proyecto404.socialnetwork.core.domain.auth

interface SessionTokenGenerator {
    fun generate(): String
}
