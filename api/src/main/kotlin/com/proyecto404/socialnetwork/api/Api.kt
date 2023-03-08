package com.proyecto404.socialnetwork.api

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.controllers.*
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.core.domain.DomainError
import io.javalin.Javalin
import io.javalin.http.Context
import org.slf4j.LoggerFactory

class Api(private val config: ApiConfiguration) {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
    private val httpServer = Javalin.create { config ->
        config.requestLogger(::logRequest)
        config.enableCorsForAllOrigins()
    }
    private val useCaseProvider = config.useCaseProvider

    init {
        registerControllers()
        registerKnownErrors()
    }

    private fun registerKnownErrors() {
        httpServer.exception(DomainError::class.java, ::returnBadRequestError)
    }

    private fun returnBadRequestError(error: Exception, ctx: Context) {
        ctx.status(400)
        ctx.makeResponse(JsonObject().add("type", error.javaClass.simpleName).add("message", error.message))
    }

    private fun registerControllers() {
        AuthController(httpServer, useCaseProvider)
        StatusController(httpServer, config.dbChecker)
        UserController(httpServer, useCaseProvider)
        PostController(httpServer, useCaseProvider)
        FollowingController(httpServer, useCaseProvider)
    }

    fun start() {
        httpServer.start(config.port)
    }

    fun stop() {
        httpServer.stop()
    }

    private fun logRequest(ctx: Context, executionTimeMs: Float) {
        val sb = StringBuilder()
        sb.append(ctx.req.method)
        sb.append(" " + ctx.fullUrl())
        sb.append(" Response: " + ctx.res.status)
        sb.append(" - " + ctx.res.getHeader("content-type"))
        sb.append(" (" + executionTimeMs + "ms)")
        if (ctx.res.status != 200) {
            sb.appendLine()
            sb.append("Request Body: " + ctx.body())
            sb.appendLine()
            sb.append("Response Body: " + ctx.resultString())
            logger.error(sb.toString())
            return
        }
        logger.info(sb.toString())
    }
}
