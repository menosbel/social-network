package com.proyecto404.socialnetwork.core.infrastructure.db

import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.Users
import com.proyecto404.socialnetwork.core.infrastructure.RepositoryFactory
import kotlin.reflect.KClass

class DbRepositoryFactory(private val credentials: Credentials): RepositoryFactory() {
    override fun <T: Any> create(repositoryClass: KClass<T>): T {
        return when(repositoryClass) {
            Users::class -> DbUsers(credentials)
            Posts::class -> DbPosts(credentials)
            Followings::class -> DbFollowings(credentials)
            else -> throw Exception("Db${repositoryClass} not implemented")
        } as T
    }
}
