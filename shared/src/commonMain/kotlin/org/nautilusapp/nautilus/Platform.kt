package org.nautilusapp.nautilus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform