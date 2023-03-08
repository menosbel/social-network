package com.proyecto404.socialnetwork.console.formatters

import com.proyecto404.socialnetwork.core.domain.Clock
import java.time.LocalDateTime

class StoppedClock private constructor (private val now: LocalDateTime): Clock {
    override fun now() = now

    companion object {
        fun at(now: LocalDateTime) = StoppedClock(now)
    }
}
