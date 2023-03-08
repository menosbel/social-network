@file:Suppress("UNCHECKED_CAST")

package com.proyecto404.socialnetwork.core.infrastructure.cache

import com.proyecto404.socialnetwork.core.infrastructure.RepositoryFactory
import kotlin.reflect.KClass

class CachedRepositoryFactory(private val factory: RepositoryFactory): RepositoryFactory() {
    private val cache = mutableMapOf<KClass<*>, Any>()

    override fun <T: Any> create(repositoryClass: KClass<T>): T {
        return cache.getOrPut(repositoryClass) { factory.create(repositoryClass) } as T
    }
}
