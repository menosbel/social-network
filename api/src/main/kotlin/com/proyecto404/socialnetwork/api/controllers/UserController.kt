package com.proyecto404.socialnetwork.api.controllers

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.extensions.getParamAsString
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.api.extensions.requestAsJsonObject
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import io.javalin.Javalin
import io.javalin.http.Context

class UserController(httpServer: Javalin, private val useCaseProvider: UseCaseProvider) {
    init {
        httpServer.post("/users", ::createUser)
    }

    private fun createUser(ctx: Context) {
        val jsonBody = ctx.requestAsJsonObject()
        val userName = jsonBody.getParamAsString("username")
        val password = jsonBody.getParamAsString("password")

        useCaseProvider.signUp().exec(userName, password)
        ctx.status(200)
        ctx.makeResponse(JsonObject())
    }
}
