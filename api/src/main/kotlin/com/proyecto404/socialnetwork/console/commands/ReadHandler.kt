package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.core.domain.Clock
import com.proyecto404.socialnetwork.console.formatters.RelativeTimeFormatter
import com.proyecto404.socialnetwork.core.application.GetUserPosts
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError

class ReadHandler(private val getUserPosts: GetUserPosts, clock: Clock): CommandHandler {
    override val commandName = "read"
    private val relativeTimeFormatter = RelativeTimeFormatter(clock)
    override fun handle(command: Command): String? {
        val userName = command.parameters.firstOrNull()
        if (userName.isNullOrBlank()) return "ERROR: Must specify a user"
        return try { doRead(userName) } catch (e: UserNotFoundError) { "ERROR: Unknown user $userName" }
    }

    private fun doRead(userName: String): String {
        val posts = getUserPosts.exec(userName).posts
        return posts.joinToString("\n") { "- ${it.message} ${relativeTimeFormatter.to(it.createdAt)}" }
    }
}
