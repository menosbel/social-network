package com.proyecto404.socialnetwork.console.commandProcessor

class CommandHandlerFactory {
    private val handlers = mutableMapOf<String, CommandHandler>()

    fun register(handler: CommandHandler) {
        handlers[handler.commandName] = handler
    }

    fun create(commandName: String): CommandHandler {
        if (!handlers.containsKey(commandName)) throw InvalidCommandError(commandName)
        return handlers[commandName]!!
    }
}
