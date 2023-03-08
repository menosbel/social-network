package com.proyecto404.socialnetwork.api.controllers

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.extensions.getParamAsString
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.api.extensions.requestAsJsonObject
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import io.javalin.Javalin
import io.javalin.http.Context

class FollowingController(httpServer: Javalin, private val useCaseProvider: UseCaseProvider) {
    init {
        httpServer.post("/users/:username/followings", ::createFollowing)
    }

    private fun createFollowing(ctx: Context) {
        val jsonBody = ctx.requestAsJsonObject()
        val sessionToken = jsonBody.getParamAsString("sessionToken")
        val userToFollow = jsonBody.getParamAsString("userToFollow")

        useCaseProvider.follow().exec(userToFollow, sessionToken)
        ctx.makeResponse(JsonObject())
    }
}
