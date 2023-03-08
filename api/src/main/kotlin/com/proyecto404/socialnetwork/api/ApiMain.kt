package com.proyecto404.socialnetwork.api

import com.proyecto404.socialnetwork.core.infrastructure.CoreConfiguration
import com.proyecto404.socialnetwork.core.infrastructure.Environment
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import com.proyecto404.socialnetwork.core.infrastructure.db.Credentials
import com.proyecto404.socialnetwork.core.infrastructure.db.DBChecker
import com.proyecto404.socialnetwork.core.infrastructure.db.DbRepositoryFactory

fun main() {
    Environment.addSearchPath("./api")

    val credentials = Credentials(Environment["DB_URL"]!!, Environment["DB_USER"]!!, Environment["DB_PASSWORD"]!!)
    val repositories = DbRepositoryFactory(credentials)
    val useCaseProvider = UseCaseProvider(CoreConfiguration(repositories = repositories))
    val dbChecker = DBChecker(credentials)
    val api = Api(ApiConfiguration(
        useCaseProvider,
        dbChecker,
        Environment["PORT"]?.toInt() ?: 80
    ))
    api.start()
}
