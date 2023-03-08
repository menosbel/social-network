package com.proyecto404.socialnetwork.core.domain

import java.time.LocalDateTime

interface Clock {
    fun now(): LocalDateTime
}
