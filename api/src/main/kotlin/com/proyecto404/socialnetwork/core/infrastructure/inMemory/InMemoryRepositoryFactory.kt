@file:Suppress("UNCHECKED_CAST")

package com.proyecto404.socialnetwork.core.infrastructure.inMemory

import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.Users
import com.proyecto404.socialnetwork.core.infrastructure.RepositoryFactory
import kotlin.reflect.KClass

class InMemoryRepositoryFactory: RepositoryFactory() {
    override fun <T: Any> create(repositoryClass: KClass<T>): T {
        return when(repositoryClass) {
            Users::class -> InMemoryUsers()
            Posts::class -> InMemoryPosts()
            Followings::class -> InMemoryFollowings()
            else -> throw Exception("InMemory${repositoryClass} not implemented")
        } as T
    }
}
