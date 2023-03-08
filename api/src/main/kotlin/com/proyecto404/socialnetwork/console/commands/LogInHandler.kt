package com.proyecto404.socialnetwork.console.commands

import com.proyecto404.socialnetwork.console.commandProcessor.Command
import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandler
import com.proyecto404.socialnetwork.console.session.CurrentSession
import com.proyecto404.socialnetwork.core.application.LogIn
import com.proyecto404.socialnetwork.core.domain.auth.InvalidCredentialsError
import com.proyecto404.socialnetwork.core.domain.user.UserNotFoundError

class LogInHandler(private val logIn: LogIn, private var currentSession: CurrentSession): CommandHandler {
    override val commandName = "login"

    override fun handle(command: Command): String? {
        return try {
            doLogin(command)
        } catch (e: InvalidCredentialsError) {
            credentialsError(command)
        } catch (e: UserNotFoundError) {
            usernameError()
        }
    }

    private fun doLogin(command: Command): String {
        val response = logIn.exec(command.userName(), command.password())
        currentSession.authenticate(command.userName(), response.sessionToken)
        return "Logged in as ${command.userName()}!"
    }

    private fun credentialsError(command: Command) = "ERROR: Invalid credentials for ${command.userName()}"

    private fun usernameError() = "ERROR: Invalid username"

    private fun Command.userName() = parameters[0]

    private fun Command.password() = parameters[1]
}
