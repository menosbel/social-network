package com.proyecto404.socialnetwork.console.session.auth

class UserIdentity(val userName: String, val sessionToken: String): Identity {
    override val isAuthenticated = true

    override fun equals(other: Any?) = other is UserIdentity && other.userName == userName && other.sessionToken == sessionToken

    override fun hashCode(): Int {
        var result = userName.hashCode()
        result = 31 * result + sessionToken.hashCode()
        return result
    }

    override fun toString() = "UserIdentity($userName, $sessionToken)"
}
