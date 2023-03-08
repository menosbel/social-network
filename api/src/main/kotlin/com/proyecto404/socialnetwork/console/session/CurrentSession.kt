package com.proyecto404.socialnetwork.console.session

import com.proyecto404.socialnetwork.console.session.auth.Identity

interface CurrentSession {
    val identity: Identity

    fun authenticate(userName: String, sessionToken: String = "")
    fun logout()
}
