package com.proyecto404.socialnetwork.console.io

class SystemInput: Input {
    override fun readLine() = kotlin.io.readLine() ?: ""
}
