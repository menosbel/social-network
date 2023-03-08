package com.proyecto404.socialnetwork.console.commandProcessor

interface CommandHandler {
    val commandName: String

    fun handle(command: Command): String?
}