package org.team2658.nautilus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform