package com.proyecto404.socialnetwork.core.infrastructure

import io.github.cdimascio.dotenv.dotenv
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Environment {
    private val searchPaths = mutableListOf<String>()
    private val dotenv by lazy {
        dotenv {
            ignoreIfMissing = true
            directory = findEnvDistDirectory()
        }
    }

    fun addSearchPath(path: String) { searchPaths.add(path) }

    operator fun get(name: String): String? {
        return dotenv[name]
    }

    operator fun get(name: String, defaultValue: String = ""): String {
        return dotenv[name] ?: defaultValue
    }

    fun getOrThrow(name: String): String {
        return dotenv[name] ?: throw IllegalArgumentException("Missing environment variable $name")
    }

    private fun getPath(path: String) = Paths.get(path).toAbsolutePath().normalize()

    private fun envDistExists(path: Path) = Files.exists(path.resolve(".env.dist"))

    private fun findEnvDistDirectory(): String {
        for (searchPath in searchPaths) {
            val path = getPath(searchPath)
            if (envDistExists(path)) return path.toString()
        }

        var path = getPath("./")
        do {
            if (envDistExists(path)) return path.toString()
            path = path.parent
        } while (path != null)
        return "./"
    }
}
