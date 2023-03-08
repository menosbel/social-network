package com.proyecto404.socialnetwork.core.infrastructure.db

class NullDbChecker: DBChecker(Credentials("", "", "")) {
    override fun isHealthy() = true
}
