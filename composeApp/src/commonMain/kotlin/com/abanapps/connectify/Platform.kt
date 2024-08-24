package com.abanapps.connectify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform