package com.proyecto404.socialnetwork.console

import com.proyecto404.socialnetwork.console.io.Input
import com.proyecto404.socialnetwork.console.io.Output
import com.proyecto404.socialnetwork.console.io.SystemInput
import com.proyecto404.socialnetwork.console.io.SystemOutput
import com.proyecto404.socialnetwork.console.session.CurrentSession
import com.proyecto404.socialnetwork.console.session.InMemoryCurrentSession
import com.proyecto404.socialnetwork.core.domain.Clock
import com.proyecto404.socialnetwork.core.infrastructure.time.SystemClock
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider

class ApplicationConfiguration(
    val useCaseProvider: UseCaseProvider,
    val currentSession: CurrentSession = InMemoryCurrentSession(),
    val input: Input = SystemInput(),
    val output: Output = SystemOutput(),
    val clock: Clock = SystemClock(),
)
