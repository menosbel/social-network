package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.core.application.SignUp

class SignUpHandler(private val signUp: SignUp): CommandHandler {
    override val commandName = "signup"

    override fun handle(command: Command): String? {
        val userName = command.parameters[0]
        val password = command.parameters[1]
        signUp.exec(userName, password)
        return null
    }
}
