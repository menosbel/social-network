package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.console.session.CurrentSession
import com.proyecto404.socialnetwork.console.session.auth.UserIdentity
import com.proyecto404.socialnetwork.core.application.Follow
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError

class FollowHandler(private val follow: Follow, private val currentSession: CurrentSession): CommandHandler {
    override val commandName = "follow"

    override fun handle(command: Command): String? {
        return try { doFollow(command) } catch (e: UserNotFoundError) { "ERROR: Must specify an user" }
    }

    private fun doFollow(command: Command): String {
        if (!currentSession.identity.isAuthenticated) return "ERROR: User must be logged to follow another user"
        val userIdentity = currentSession.identity as UserIdentity
        return follow.exec(command.parameters.firstOrNull() ?: "", userIdentity.sessionToken)
    }
}
