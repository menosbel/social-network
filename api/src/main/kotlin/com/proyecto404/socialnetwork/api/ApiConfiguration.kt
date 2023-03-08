package com.proyecto404.socialnetwork.api

import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import com.proyecto404.socialnetwork.core.infrastructure.db.DBChecker

class ApiConfiguration(
    val useCaseProvider: UseCaseProvider,
    val dbChecker: DBChecker,
    val port: Int = 80,
)
