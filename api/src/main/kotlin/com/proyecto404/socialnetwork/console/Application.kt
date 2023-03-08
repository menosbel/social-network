package com.proyecto404.socialnetwork.console

import com.proyecto404.socialnetwork.console.commandProcessor.CommandHandlerFactory
import com.proyecto404.socialnetwork.console.commandProcessor.CommandProcessor
import com.proyecto404.socialnetwork.console.commandProcessor.prompt.UserPromptPrinter
import com.proyecto404.socialnetwork.console.commands.*

class Application(private val config: ApplicationConfiguration) {
    private val handlerFactory = CommandHandlerFactory()
    private val promptPrinter = UserPromptPrinter(config.currentSession)
    private val commandProcessor = CommandProcessor(config.input, config.output, handlerFactory, promptPrinter)

    init {
        initializeCommands()
    }

    private fun initializeCommands() {
        handlerFactory.apply {
            register(SignUpHandler(config.useCaseProvider.signUp()))
            register(LogInHandler(config.useCaseProvider.logIn(), config.currentSession))
            register(LogOutHandler(config.currentSession))
            register(PostHandler(config.useCaseProvider.createPost(), config.currentSession))
            register(ReadHandler(config.useCaseProvider.getUserPosts(), config.clock))
            register(FollowHandler(config.useCaseProvider.follow(), config.currentSession))
        }
    }

    fun run() {
        commandProcessor.run()
    }
}
