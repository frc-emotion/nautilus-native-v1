package org.team2658.emotion.auth

import org.team2658.emotion.User

data class LoginResult(
    val success: Boolean,
    val data: User? = null,
)
