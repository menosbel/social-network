package com.proyecto404.socialnetwork.api.extensions

import com.eclipsesource.json.JsonObject

fun JsonObject.getParamAsString(paramName: String): String = this[paramName].asString()