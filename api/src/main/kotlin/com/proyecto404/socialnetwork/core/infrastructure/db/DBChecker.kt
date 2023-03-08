package com.proyecto404.socialnetwork.core.infrastructure.db

import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.Tables.USERS
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory
import java.sql.SQLException

open class DBChecker(private val credentials: Credentials) {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)

    private fun context() = DSL.using(credentials.url, credentials.user, credentials.password)

    open fun isHealthy(): Boolean {
        try {
            context().fetchCount(USERS)
            return true
        } catch (e: SQLException) {
            logger.error(e.message, e)
            return false
        }
    }
}
