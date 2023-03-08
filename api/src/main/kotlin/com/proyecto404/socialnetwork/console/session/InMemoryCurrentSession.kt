package com.proyecto404.socialnetwork.console.session

import com.proyecto404.socialnetwork.console.session.auth.AnonymousIdentity
import com.proyecto404.socialnetwork.console.session.auth.Identity
import com.proyecto404.socialnetwork.console.session.auth.UserIdentity

class InMemoryCurrentSession: CurrentSession {
    override var identity: Identity = AnonymousIdentity()
        private set

    override fun authenticate(userName: String, sessionToken: String) {
        identity = UserIdentity(userName, sessionToken)
    }

    override fun logout() {
        identity = AnonymousIdentity()
    }
}
