package org.team2658.emotion.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.team2658.emotion.userauth.AccessLevel
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import org.team2658.emotion.userauth.UserPermissions

class SettingsViewModel : ViewModel() {
    //TODO: instantiate with GetAuthState once implemented
    var authState by mutableStateOf(AuthState.NOT_LOGGED_IN)
        private set

    //TODO: instantiate with GetUser once implemented
    var user: User? by mutableStateOf(null)
        private set


    fun login(username: String, password: String) {
        //TODO: user = GetUser()
        user = when (username.trim()) {
            "nova", "novamondal", "admin" -> User(
                username,
                "JWT",
                "Nova",
                "Mondal",
                "email@example.com",
                AccessLevel.ADMIN,
                permissions = UserPermissions(
                    submitScoutingData = true,
                    inPitScouting = true,
                    viewScoutingData = true,
                )
            )

            "scout", "scouter", "test" -> User(
                username,
                "JWT",
                "Scouter",
                "Example",
                "email@example.com",
                AccessLevel.BASE,
                permissions = UserPermissions(
                    submitScoutingData = true,
                    inPitScouting = true,
                    viewScoutingData = true,
                )
            )

            else -> User(
                username,
                "JWT",
                "Base",
                "Example",
                "email@example.com",
                AccessLevel.BASE,
                permissions = UserPermissions(
                    submitScoutingData = false,
                    inPitScouting = false,
                    viewScoutingData = false,
                )
            )
        }
        authState = AuthState.LOGGED_IN
    }

    fun logout() {
        //TODO()
        user = null
        authState = AuthState.NOT_LOGGED_IN
    }

    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam
    ) {
        //TODO(username, password, email, firstName, lastName)
        //TODO: user = GetUser()
        user = User(
            username,
            "JWT",
            firstName,
            lastName,
            email,
            AccessLevel.NONE,
            subteam = subteam
        ) //TODO: remove this line
        authState = AuthState.AWAITING_VERIFICATION
    }
}