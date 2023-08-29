package org.team2658.emotion

data class User(
    val username: String,
    val JWT: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val accessLevel: AccessLevel = AccessLevel.NONE
)