package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.console.session.CurrentSession

class LogOutHandler(private val currentSession: CurrentSession): CommandHandler {
    override val commandName = "logout"

    override fun handle(command: Command): String {
        currentSession.logout()
        return "Logged out!"
    }
}
