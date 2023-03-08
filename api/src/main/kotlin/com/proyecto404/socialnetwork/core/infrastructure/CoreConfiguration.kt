package com.proyecto404.socialnetwork.core.infrastructure

import com.proyecto404.socialnetwork.core.domain.Clock
import com.proyecto404.socialnetwork.core.domain.auth.SessionTokenGenerator
import com.proyecto404.socialnetwork.core.infrastructure.auth.RandomSessionTokenGenerator
import com.proyecto404.socialnetwork.core.infrastructure.time.SystemClock

class CoreConfiguration(
    val sessionTokenGenerator: SessionTokenGenerator = RandomSessionTokenGenerator(),
    val clock: Clock = SystemClock(),
    val repositories: RepositoryFactory,
)
