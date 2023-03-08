package com.proyecto404.socialnetwork.api.controllers

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.extensions.getParamAsString
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.api.extensions.requestAsJsonObject
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import io.javalin.Javalin
import io.javalin.http.Context

class AuthController(httpServer: Javalin, private val useCaseProvider: UseCaseProvider) {
    init {
        httpServer.post("/login", ::login)
    }

    private fun login(ctx: Context) {
        val jsonBody = ctx.requestAsJsonObject()
        val userName = jsonBody.getParamAsString("username")
        val password = jsonBody.getParamAsString("password")

        val response = useCaseProvider.logIn().exec(userName, password)

        ctx.makeResponse(JsonObject().add("sessionToken", response.sessionToken))
    }
}
