@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.console.commandProcessor.prompt

import com.proyecto404.socialnetwork.console.io.Output
import com.proyecto404.socialnetwork.console.session.CurrentSession
import com.proyecto404.socialnetwork.console.session.auth.UserIdentity

class UserPromptPrinter(private val currentSession: CurrentSession): PromptPrinter {
    private val PROMPT = "> "

    override fun print(output: Output) {
        if (!currentSession.identity.isAuthenticated) { output.print(PROMPT); return }
        val user = currentSession.identity as UserIdentity
        output.print("${user.userName} $PROMPT")
    }
}
