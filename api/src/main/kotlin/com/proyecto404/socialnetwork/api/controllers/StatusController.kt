package com.proyecto404.socialnetwork.api.controllers

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.core.infrastructure.db.DBChecker
import io.javalin.Javalin
import io.javalin.http.Context

class StatusController(httpServer: Javalin, private val dbChecker: DBChecker) {
    init {
        httpServer.get("/status", ::status)
    }

    private fun status(ctx: Context) {
        var status = "OK"
        if (!dbChecker.isHealthy()) {
            status = "db unhealthy"
        }

        ctx.makeResponse(JsonObject().add("status", status))
    }
}
