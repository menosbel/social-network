package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.console.session.CurrentSession
import com.proyecto404.socialnetwork.console.session.auth.UserIdentity
import com.proyecto404.socialnetwork.core.application.CreatePost
import com.proyecto404.socialnetwork.core.domain.post.InvalidMessageError

class PostHandler(private val post: CreatePost, private val currentSession: CurrentSession): CommandHandler {
    override val commandName = "post"

    override fun handle(command: Command): String? {
        return try { doPost(command) } catch (e: InvalidMessageError) { "ERROR: Empty message" }
    }

    private fun doPost(command: Command): String? {
        if (!currentSession.identity.isAuthenticated) return "ERROR: User must be logged to post messages"
        val userIdentity = currentSession.identity as UserIdentity
        post.exec(getMessage(command), userIdentity.sessionToken)
        return null
    }

    private fun getMessage(command: Command) = command.parameters.joinToString(" ")
}
