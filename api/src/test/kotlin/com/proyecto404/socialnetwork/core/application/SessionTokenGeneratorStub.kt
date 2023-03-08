package com.proyecto404.socialnetwork.core.application

import com.proyecto404.socialnetwork.core.domain.auth.SessionTokenGenerator

class SessionTokenGeneratorStub(private val sessionToken: String): SessionTokenGenerator {
    override fun generate() = sessionToken
}
