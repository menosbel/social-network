package com.proyecto404.socialnetwork.console

import com.proyecto404.socialnetwork.console.io.Input

class FakeInput(private val output: FakeOutput) : Input {
    private val lines = mutableListOf<String>()
    private var current = 0

    fun willRead(vararg line: String) {
        lines.addAll(line)
    }

    override fun readLine(): String {
        val text = if (current >= lines.size) "" else lines[current++]
        output.println(text)
        return text
    }
}