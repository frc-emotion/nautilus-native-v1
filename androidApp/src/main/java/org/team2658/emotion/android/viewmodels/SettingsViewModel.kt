package org.team2658.emotion.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Role
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
                "Nova",
                "Mondal",
                username,
                "email@example.com",
                69696969,
                accountType = AccountType.ADMIN,
                roles = listOf(
                    Role(
                        "admin", UserPermissions(
                            standScouting = true,
                            inPitScouting = true,
                            viewScoutingData = true,
                            makeAnnouncements = true,
                            makeBlogPosts = true,
                            verifyAllAttendance = true,
                            verifySubteamAttendance = true,
                        )
                    )
                ),
                subteam = Subteam.SOFTWARE,
                grade = 12,
                token = "adfkasjfasjf",
            )


            "scout", "scouter", "test" -> User(
                "Scouter",
                "User",
                username,
                "email@example.com",
                69696969,
                accountType = AccountType.BASE,
                roles = listOf(
                    Role(
                        "scout", UserPermissions(
                            standScouting = true,
                            inPitScouting = true,
                            viewScoutingData = true,
                        )
                    )
                ),
                subteam = Subteam.SOFTWARE,
                grade = 12,
                token = "adfkasjfasjf",
            )

            else -> User(
                "Base",
                "User",
                username,
                "email@example.com",
                69696969,
                accountType = AccountType.BASE,
                roles = listOf(
                    Role(
                        "scout", UserPermissions()
                    )
                ),
                subteam = Subteam.SOFTWARE,
                grade = 12,
                token = "adfkasjfasjf",
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
        subteam: Subteam,
        phone: Int,
        grade: Int,
        //TODO: parent and advisor account types
    ) {
        //TODO(username, password, email, firstName, lastName)
        //TODO: user = GetUser()
        user = User(
            firstName,
            lastName,
            username,
            email,
            phone,
            accountType = AccountType.UNVERIFIED,
            roles = listOf(
                Role(
                    "scout", UserPermissions()
                )
            ),
            subteam = subteam,
            grade = grade,
            token = null,
        )
        authState = AuthState.AWAITING_VERIFICATION
    }
}