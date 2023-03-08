package com.proyecto404.socialnetwork.core.domain

fun String.ifBlank(block: () -> Unit) {
    if (this.isBlank()) block()
}