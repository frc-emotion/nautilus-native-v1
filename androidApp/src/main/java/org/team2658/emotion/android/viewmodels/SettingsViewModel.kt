package org.team2658.emotion.android.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.team2658.emotion.AccessLevel
import org.team2658.emotion.AuthState
import org.team2658.emotion.User

class SettingsViewModel: ViewModel() {
    //TODO: instantiate with GetAuthState once implemented
    var authState by mutableStateOf(AuthState.NOT_LOGGED_IN)
        private set

    //TODO: instantiate with GetUser once implemented
    var user: User? by mutableStateOf(null)
        private set


    fun login(username: String, password: String) {
       //TODO(username, password)
        //TODO: user = GetUser()
        user = User(username, "JWT", "firstName", "lastName", "email", AccessLevel.SCOUTER) //TODO: remove this line
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
        email:String,
        firstName: String,
        lastName: String) {
        //TODO(username, password, email, firstName, lastName)
        //TODO: user = GetUser()
        user = User(username, "JWT", firstName, lastName, email, AccessLevel.SCOUTER) //TODO: remove this line
        authState = AuthState.AWAITING_VERIFICATION
    }
}