package com.proyecto404.socialnetwork.api.extensions

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import io.javalin.http.Context

fun Context.makeResponse(json: JsonObject) {
    this.contentType("application/json")
    this.result(json.toString())
}

fun Context.requestAsJsonObject(): JsonObject = Json.parse(this.body()).asObject()