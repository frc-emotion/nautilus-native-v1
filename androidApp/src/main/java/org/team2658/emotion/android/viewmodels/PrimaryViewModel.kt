package org.team2658.emotion.android.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrimaryViewModel(private val ktorClient: EmotionClient, private val sharedPref: SharedPreferences) : ViewModel() {
    var user: User? by mutableStateOf(User.fromJSON(sharedPref.getString("user", null)))
        private set

    fun updateUser(user: User?) {
        this.user = user
        with(sharedPref.edit()) {
            putString("user", user?.toJSON())
            apply()
        }
    }

    suspend fun updateMe() {
        println("Fetching updated user")
        val updated = this.ktorClient.getMe(this.user)
            updated?.let {
                updateUser(it)
                println("Fetched updated user")
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

    fun getClient(): EmotionClient {
        return this.ktorClient
    }

    suspend fun login(username: String, password: String) {
        updateUser(this.ktorClient.login(username, password))
        this.authState = if(this.user != null) AuthState.LOGGED_IN else AuthState.NOT_LOGGED_IN
    }

    fun logout() {
        //TODO()
        updateUser(null)
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
        updateUser(this.ktorClient.register(username, password, email, firstName, lastName, subteam, phone, grade))
        authState = if(this.user != null) AuthState.AWAITING_VERIFICATION else AuthState.NOT_LOGGED_IN
    }

    suspend fun testMeeting(): Meeting? {
        return this.ktorClient.createMeeting(
            user = this.user,
            startTime = 0,
            endTime = 1695075737873L,
            type = "Test Meeting",
            description = "This is a test meeting",
            value = 1
        )
    }

    var meeting: Meeting? by mutableStateOf(Meeting.fromJSON(sharedPref.getString("createdMeeting", null)))
        private set

    fun updateMeeting(meeting: Meeting?) {
        this.meeting = meeting
        with(sharedPref.edit()) {
            putString("createdMeeting", meeting?.toJson())
            apply()
        }
    }

}