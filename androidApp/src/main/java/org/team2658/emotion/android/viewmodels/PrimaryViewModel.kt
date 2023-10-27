package org.team2658.emotion.android.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.room.dbs.ScoutingDB
import org.team2658.emotion.android.room.entities.ChargedUpEntity
import org.team2658.emotion.android.room.entities.chargedUpParamsFromEntity
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.scouting.GameResult
import org.team2658.emotion.scouting.scoutingdata.ChargedUp
import org.team2658.emotion.scouting.scoutingdata.RapidReact
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User

class PrimaryViewModel(private val ktorClient: EmotionClient, private val sharedPref: SharedPreferences, private val db: ScoutingDB) : ViewModel() {
    var user: User? by mutableStateOf(User.fromJSON(sharedPref.getString("user", null)))
        private set

    val dao = db.chargedUpDao

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
            AccountType.BASE, AccountType.LEAD, AccountType.ADMIN, AccountType.SUPERUSER -> AuthState.LOGGED_IN
            null -> AuthState.NOT_LOGGED_IN
        }
    )
        private set

    fun getClient(): EmotionClient {
        return this.ktorClient
    }

    suspend fun login(username: String, password: String, errorCallback: (String) -> Unit) {
        updateUser(this.ktorClient.login(username, password, errorCallback))
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
        errorCallback: (String) -> Unit
        //TODO: parent and advisor account types
    ) {
        updateUser(this.ktorClient.register(username, password, email, firstName, lastName, subteam, phone, grade, errorCallback))
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

    suspend fun getCompetitions(year: String): List<String> {
        return this.ktorClient.getCompetitions(year) //TODO: cache offline
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

    suspend fun submitRapidReact(user: User?, data: RapidReact):Boolean {
        delay(1000L)
        return true;
    }

    suspend fun submitChargedUp(user: User?, data: ChargedUp):Boolean {
        val entity = ChargedUpEntity(
            won = data.gameResult == GameResult.WIN,
            tied = data.gameResult == GameResult.TIE,
            score = data.finalScore,
            penaltyCount = data.penaltyPointsEarned,
            competition = data.competition,
            matchNumber = data.matchNumber,
            teamNumber = data.teamNumber,
            sustainRP = data.RPEarned[0],
            activationRP = data.RPEarned[1],
            totalRP = data.totalRP,
            autoBotCones = data.autoPeriod.botCones,
            autoBotCubes = data.autoPeriod.botCubes,
            autoMidCones = data.autoPeriod.midCones,
            autoMidCubes = data.autoPeriod.midCubes,
            autoTopCones = data.autoPeriod.topCones,
            autoTopCubes = data.autoPeriod.topCubes,
            teleopBotCones = data.teleopPeriod.botCones,
            teleopBotCubes = data.teleopPeriod.botCubes,
            teleopMidCones = data.teleopPeriod.midCones,
            teleopMidCubes = data.teleopPeriod.midCubes,
            teleopTopCones = data.teleopPeriod.topCones,
            teleopTopCubes = data.teleopPeriod.topCubes,
            linkScore = data.linkScore,
            autoDock = data.autoDock,
            autoEngage = data.autoEngage,
            teleopDock = data.teleopDock,
            teleopEngage = data.teleopEngage,
            parked = data.parked,
            isDefensive = data.defensive,
            didBreak = data.brokeDown,
            comments = data.comments
        )
        var success = false
        val params = chargedUpParamsFromEntity(entity)
        withContext(Dispatchers.IO) {
            val res = ktorClient.submitChargedUp(params, user)
            println(res)
            success = res != null
            if (!success) try {
                dao.insertChargedUp(entity)
                success = true
            } catch (e: Exception) {
                println(e)
            }
        }
        return success
    }


}