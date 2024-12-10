package org.example.wakil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform