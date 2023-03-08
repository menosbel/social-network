package com.proyecto404.socialnetwork.console.commandProcessor

import com.proyecto404.socialnetwork.console.commandProcessor.prompt.PromptPrinter
import com.proyecto404.socialnetwork.console.commandProcessor.prompt.SimplePromptPrinter
import com.proyecto404.socialnetwork.console.io.Input
import com.proyecto404.socialnetwork.console.io.Output

class CommandProcessor(
    private val input: Input,
    private val output: Output,
    private val handlerFactory: CommandHandlerFactory,
    private val promptPrinter: PromptPrinter = SimplePromptPrinter("> "),
) {
    fun run() {
        while (true) {
            val command = readCommand()
            if (isExit(command)) break
            process(command)
        }
        output.println("bye bye")
    }

    private fun isExit(command: Command) = command.name == "exit"

    private fun process(command: Command) {
        try {
            val outputStr = handlerFactory.create(command.name).handle(command)
            if (outputStr != null) output.println(outputStr)
        } catch (e: InvalidCommandError) {
            output.println("ERROR: invalid command ${command.name}")
        }
    }

    private fun readCommand(): Command {
        promptPrinter.print(output)
        val line = input.readLine()
        val split = line.split(" ")
        return Command(split.first(), split.drop(1))
    }
}
