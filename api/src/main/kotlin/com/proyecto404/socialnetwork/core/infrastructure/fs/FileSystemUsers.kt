package com.proyecto404.socialnetwork.core.infrastructure.fs

import com.google.gson.Gson
import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.domain.user.Users
import java.io.File
import java.nio.file.Paths

class FileSystemUsers: Users {
    private val storagePath = Paths.get("storage/users").toAbsolutePath().toString()

    override fun nextId(): UserId {
        val maxId = allUsers().maxOfOrNull { it.id.toInt() } ?: 0
        return UserId(maxId + 1)
    }

    override fun get(id: UserId): User {
        val file = File("$storagePath/${id.toInt()}.json")
        val userJson = file.readText(Charsets.UTF_8)
        val snapshot = Gson().fromJson(userJson, User.Snapshot::class.java)
        return User.fromSnapshot(snapshot)
    }

    override fun get(userName: String) = allUsers().single { it.userName == userName }

    override fun add(user: User) {
        ensureStoragePathExists()
        val file = userFile(user)
        file.createNewFile()
        file.writeText(getJson(user), Charsets.UTF_8)
    }

    override fun update(user: User) {
        ensureStoragePathExists()
        val file = userFile(user)
        file.writeText(getJson(user), Charsets.UTF_8)
    }

    private fun userFile(user: User) = File("$storagePath/${user.id.toInt()}.json")

    private fun getJson(user: User): String {
        val snapshot = user.snapshot()
        return Gson().toJson(snapshot)
    }

    private fun ensureStoragePathExists() {
        val storage = File(storagePath)
        if (storage.exists()) return
        storage.mkdirs()
    }

    override fun findBySessionToken(sessionToken: String): User? {
        return allUsers().firstOrNull { it.sessionToken == sessionToken }
    }

    private fun allUsers(): Sequence<User> {
        ensureStoragePathExists()
        return File(storagePath).walk().filter { it.isFile }.map {
            get(UserId(it.nameWithoutExtension.toInt()))
        }
    }
}
