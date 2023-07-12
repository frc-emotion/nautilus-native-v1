package org.team2658.emotion

fun String.toCapitalized(): String {
    return this.substring(0, 1).uppercase() + this.lowercase().substring(1)
}