package com.proyecto404.socialnetwork.core.infrastructure

import com.proyecto404.socialnetwork.core.infrastructure.cache.CachedRepositoryFactory
import kotlin.reflect.KClass

abstract class RepositoryFactory {
    abstract fun <T: Any> create(repositoryClass: KClass<T>): T

    inline fun <reified T: Any> create() = create(T::class)

    fun cached() = CachedRepositoryFactory(this)
}
