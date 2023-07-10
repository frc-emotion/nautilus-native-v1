package org.team2658.emotion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform