package com.proyecto404.socialnetwork.core.infrastructure.time

import com.proyecto404.socialnetwork.core.domain.Clock
import java.time.LocalDateTime

class SystemClock: Clock {
    override fun now(): LocalDateTime = LocalDateTime.now()
}
