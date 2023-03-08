package com.proyecto404.socialnetwork.console

import com.proyecto404.socialnetwork.core.infrastructure.CoreConfiguration
import com.proyecto404.socialnetwork.core.infrastructure.Environment
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import com.proyecto404.socialnetwork.core.infrastructure.db.Credentials
import com.proyecto404.socialnetwork.core.infrastructure.db.DbRepositoryFactory

fun main() {
    Environment.addSearchPath("./api")

    val repositories = DbRepositoryFactory(Credentials(Environment["DB_URL"]!!, Environment["DB_USER"]!!, Environment["DB_PASSWORD"]!!))
    val config = ApplicationConfiguration(UseCaseProvider(CoreConfiguration(repositories = repositories)))
    Application(config).run()
}
