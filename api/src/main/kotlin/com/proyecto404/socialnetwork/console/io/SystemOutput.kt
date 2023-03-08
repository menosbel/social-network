package com.proyecto404.socialnetwork.console.io

class SystemOutput: Output {
    override fun println(text: String) {
        kotlin.io.println(text)
    }

    override fun print(text: String) {
        kotlin.io.print(text)
    }
}
