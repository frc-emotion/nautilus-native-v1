package org.team2658.emotion.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.delay
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Role
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import org.team2658.emotion.userauth.UserPermissions

class SettingsViewModel(private val ktorClient: EmotionClient) : ViewModel() {
    //TODO: user is not persisted between app restarts, so user needs to log back in every time app is lauched. fix by persisting user in localstorage
    var authState: AuthState by mutableStateOf(AuthState.NOT_LOGGED_IN)
        private set

    //TODO: instantiate with GetUser once implemented
    var user: User? by mutableStateOf(null)
        private set


    suspend fun login(username: String, password: String) {
//        //TODO: user = GetUser()
//        delay(100L) //to simulate waiting for network response
//        user = when (username.trim()) {
//            "nova", "novamondal", "admin" -> User(
//                "Nova",
//                "Mondal",
//                username,
//                "email@example.com",
//                69696969,
//                accountType = AccountType.ADMIN,
//                roles = listOf(
//                    Role(
//                        "admin", UserPermissions(
//                            standScouting = true,
//                            inPitScouting = true,
//                            viewScoutingData = true,
//                            makeAnnouncements = true,
//                            makeBlogPosts = true,
//                            verifyAllAttendance = true,
//                            verifySubteamAttendance = true,
//                        )
//                    )
//                ),
//                subteam = Subteam.SOFTWARE,
//                grade = 12,
//                token = "adfkasjfasjf",
//            )
//
//
//            "scout", "scouter", "test" -> User(
//                "Scouter",
//                "User",
//                username,
//                "email@example.com",
//                69696969,
//                accountType = AccountType.BASE,
//                roles = listOf(
//                    Role(
//                        "scout", UserPermissions(
//                            standScouting = true,
//                            inPitScouting = true,
//                            viewScoutingData = true,
//                        )
//                    )
//                ),
//                subteam = Subteam.SOFTWARE,
//                grade = 12,
//                token = "adfkasjfasjf",
//            )
//
//            else -> User(
//                "Base",
//                "User",
//                username,
//                "email@example.com",
//                69696969,
//                accountType = AccountType.BASE,
//                roles = listOf(
//                    Role(
//                        "scout", UserPermissions()
//                    )
//                ),
//                subteam = Subteam.SOFTWARE,
//                grade = 12,
//                token = "adfkasjfasjf",
//            )
//        }
//        authState = AuthState.LOGGED_IN
        this.user = this.ktorClient.login(username, password)
        this.authState = if(this.user != null) AuthState.LOGGED_IN else AuthState.NOT_LOGGED_IN
    }

    fun logout() {
        //TODO()
        user = null
        authState = AuthState.NOT_LOGGED_IN
    }

    suspend fun register(
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
        delay(200L)
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