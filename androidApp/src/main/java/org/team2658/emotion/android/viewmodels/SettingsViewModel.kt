package org.team2658.emotion.android.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Role
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import org.team2658.emotion.userauth.UserPermissions

class SettingsViewModel(private val ktorClient: EmotionClient, private val sharedPref: SharedPreferences) : ViewModel() {
    var user: User? by mutableStateOf(User.fromJSON(sharedPref.getString("user", null)))
        private set

    private fun setThisUser(user: User?) {
        this.user = user
        with(sharedPref.edit()) {
            putString("user", user?.toJSON())
            apply()
        }
    }

    var authState: AuthState by mutableStateOf(
        when(this.user?.accountType) {
        AccountType.UNVERIFIED -> AuthState.AWAITING_VERIFICATION
        AccountType.BASE, AccountType.LEAD, AccountType.ADMIN -> AuthState.LOGGED_IN
        else -> AuthState.NOT_LOGGED_IN
        }
    )
        private set

    suspend fun login(username: String, password: String) {
        setThisUser(this.ktorClient.login(username, password))
        this.authState = if(this.user != null) AuthState.LOGGED_IN else AuthState.NOT_LOGGED_IN
    }

    fun logout() {
        //TODO()
        setThisUser(null)
        this.authState = AuthState.NOT_LOGGED_IN
    }

    suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam,
        phone: String,
        grade: Int,
        //TODO: parent and advisor account types
    ) {
        setThisUser(this.ktorClient.register(username, password, email, firstName, lastName, subteam, phone, grade))
        authState = if(this.user != null) AuthState.AWAITING_VERIFICATION else AuthState.NOT_LOGGED_IN
    }
}