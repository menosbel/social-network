package com.proyecto404.socialnetwork.console.session.auth

class AnonymousIdentity: Identity {
    override val isAuthenticated = false

    override fun equals(other: Any?) = other is AnonymousIdentity
    override fun hashCode() = javaClass.hashCode()
    override fun toString() = "AnonymousIdentity"
}
