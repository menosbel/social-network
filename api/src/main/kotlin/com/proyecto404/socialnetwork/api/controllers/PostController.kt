package com.proyecto404.socialnetwork.api.controllers

import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.extensions.getParamAsString
import com.proyecto404.socialnetwork.api.extensions.makeResponse
import com.proyecto404.socialnetwork.api.extensions.requestAsJsonObject
import com.proyecto404.socialnetwork.core.application.GetUserPosts
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import io.javalin.Javalin
import io.javalin.http.Context
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class PostController(httpServer: Javalin, private val useCaseProvider: UseCaseProvider) {
    init {
        httpServer.get("/users/:username/posts", ::userPosts)
        httpServer.post("/users/:username/posts", ::createPost)
    }

    private fun createPost(ctx: Context) {
        val jsonBody = ctx.requestAsJsonObject()
        val sessionToken = jsonBody.getParamAsString("sessionToken")
        val message = jsonBody.getParamAsString("message")
        val response = useCaseProvider.createPost().exec(message, sessionToken)
        ctx.makeResponse(JsonObject().add("postId", response.toInt()))
    }

    private fun userPosts(ctx: Context) {
        val username = ctx.pathParam("username")
        val response = useCaseProvider.getUserPosts().exec(username)
        ctx.makeResponse(toJson(response))
    }

    private fun toJson(response: GetUserPosts.Response): JsonObject {
        val postsJson = JsonArray()
        response.posts.forEach {
            postsJson.add(JsonObject()
                .add("id", it.id)
                .add("message", it.message)
                .add("createdAt", it.createdAt.toISO8601())
            )
        }
        return JsonObject().add("posts", postsJson)
    }

    private fun LocalDateTime.toISO8601(): String {
        return this.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }
}
