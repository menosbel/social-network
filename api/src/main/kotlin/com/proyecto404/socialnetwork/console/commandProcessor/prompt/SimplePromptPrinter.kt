package com.proyecto404.socialnetwork.console.commandProcessor.prompt

import com.proyecto404.socialnetwork.console.io.Output

class SimplePromptPrinter(private val prompt: String): PromptPrinter {
    override fun print(output: Output) {
        output.print(prompt)
    }
}
