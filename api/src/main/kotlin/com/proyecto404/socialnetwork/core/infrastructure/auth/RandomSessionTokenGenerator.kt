package com.proyecto404.socialnetwork.core.infrastructure.auth

import com.proyecto404.socialnetwork.core.domain.auth.SessionTokenGenerator
import java.util.*

class RandomSessionTokenGenerator: SessionTokenGenerator {
    override fun generate() = UUID.randomUUID().toString()
}
