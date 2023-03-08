package com.proyecto404.socialnetwork.console.formatters

import com.proyecto404.socialnetwork.core.domain.Clock
import com.proyecto404.socialnetwork.core.infrastructure.time.SystemClock
import java.lang.Math.abs
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class RelativeTimeFormatter(private val clock: Clock = SystemClock()) {
    private val minutesInADay = 1440
    private val minutesInAMonth = minutesInADay * 30
    private val minutesInAnHour = 60L

    fun to(date: LocalDateTime) = format(date.minutesTo(clock.now()))

    private fun format(relativeMinutes: Long): String {
        return when {
            relativeMinutes == 0L -> "(just now)"
            relativeMinutes < minutesInAnHour -> "($relativeMinutes minutes ago)"
            relativeMinutes < minutesInADay -> "(${relativeMinutes / minutesInAnHour} hours ago)"
            relativeMinutes < minutesInAMonth -> "(${relativeMinutes / minutesInADay} days ago)"
            else -> "(more than a month ago)"
        }
    }

    private fun LocalDateTime.minutesTo(other: LocalDateTime) = abs(ChronoUnit.MINUTES.between(this, other))
}
