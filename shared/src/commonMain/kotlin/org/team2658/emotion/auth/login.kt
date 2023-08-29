package org.team2658.emotion.auth

import io.ktor.client.plugins.ClientRequestException
import org.team2658.apikt.EmotionApi
import org.team2658.emotion.AccessLevel
import org.team2658.emotion.User

suspend fun login(username: String, password: String): LoginResult {
    return try {
        val data = EmotionApi.login(username, password);
        LoginResult(
            true, User(
                username = data.username,
                JWT = "${data.token}", //interpolated into string to cast null to empty string
                firstName = data.firstname,
                lastName = data.lastname,
                email = data.email,
                accessLevel = getAccessLevel(data.accountType)
            )
        )
    } catch (e: ClientRequestException) {
        LoginResult(false)
    }
}

fun getAccessLevel(i: Int?): AccessLevel {
    return when (i) {
        0 -> AccessLevel.NONE
        1 -> AccessLevel.BASE
        2, 3 -> AccessLevel.ADMIN
        else -> AccessLevel.NONE
    }
}