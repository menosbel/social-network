package com.proyecto404.socialnetwork.console

import com.proyecto404.socialnetwork.console.io.Output

class FakeOutput : Output {
    var content: String = ""

    override fun println(text: String) {
        this.content += text + "\n"
    }

    override fun print(text: String) {
        content += text
    }
}