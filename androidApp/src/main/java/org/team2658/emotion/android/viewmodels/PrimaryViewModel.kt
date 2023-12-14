package org.team2658.emotion.android.viewmodels

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.room.dbs.AttendanceDB
import org.team2658.emotion.android.room.dbs.ScoutingDB
import org.team2658.emotion.android.room.entities.ChargedUpEntity
import org.team2658.emotion.android.room.entities.Competition
import org.team2658.emotion.android.room.entities.MeetingEntity
import org.team2658.emotion.android.room.entities.StorageType
import org.team2658.emotion.android.room.entities.chargedUpParamsFromEntity
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.scouting.GameResult
import org.team2658.emotion.scouting.scoutingdata.ChargedUp
import org.team2658.emotion.scouting.scoutingdata.RapidReact
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import java.time.LocalDateTime
import java.time.ZoneOffset

class PrimaryViewModel(private val ktorClient: EmotionClient,
                       private val sharedPref: SharedPreferences,
                       scoutingDB: ScoutingDB,
                       private val connectivityManager:
                       ConnectivityManager?,
                       attendanceDB: AttendanceDB,
                       private val clearNFC: () -> Unit
    ) : ViewModel() {
    var user: User? by mutableStateOf(User.fromJSON(sharedPref.getString("user", null)))
        private set

    private val chargedUpDao = scoutingDB.chargedUpDao

    private val compsDao = scoutingDB.compsDao

    private val meetingDao = attendanceDB.meetingDao

    private val competitionYears = listOf("2023")

    init {
        sync()
    }

    fun updateUser(user: User?) {
        this.user = user
        authState = when(user?.accountType) {
            AccountType.UNVERIFIED -> AuthState.AWAITING_VERIFICATION
            AccountType.BASE, AccountType.LEAD, AccountType.ADMIN, AccountType.SUPERUSER -> AuthState.LOGGED_IN
            null -> AuthState.NOT_LOGGED_IN
        }
        with(sharedPref.edit()) {
            putString("user", user?.toJSON())
            apply()
        }
    }

    suspend fun getChargedUpQueueLength(): Int {
        return withContext(Dispatchers.IO) {
            chargedUpDao.getChargedUpTemp().size
        }
    }

    private suspend fun syncUser() {
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

        this.authState = when(this.user?.accountType){
            null -> AuthState.NOT_LOGGED_IN
            AccountType.UNVERIFIED -> AuthState.AWAITING_VERIFICATION
            AccountType.BASE, AccountType.LEAD, AccountType.ADMIN, AccountType.SUPERUSER -> AuthState.LOGGED_IN
        }
    }

    private suspend fun cleanupDBS() {
        withContext(Dispatchers.IO) {
            cleanCompsCache()
            clearChargedUpCache()
            clearMeetingsCache()
        }

    }

    fun logout() {
        //TODO()
        updateUser(null)
        clearNFC()
        viewModelScope.launch { cleanupDBS() }

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

    suspend fun getCompetitions(year: String): List<String> {
        return withContext(Dispatchers.IO){
            try {
                compsDao.getComps(year).map { it.name }
            } catch (e: Exception) {
                println(e)
                emptyList()
            }
        }
    }

    private suspend fun cleanCompsCache() {
        withContext(Dispatchers.IO) {
           competitionYears.forEach {
               val all = compsDao.getComps(it)
               compsDao.deleteComps(all)
           }
        }
    }

    private suspend fun fetchAndStoreCompetitionsForYear(year: String) {
        withContext(Dispatchers.IO) {
            val comps = ktorClient.getCompetitions(year)
            println("fetching comps for $year")
            if (comps.isNotEmpty()) {
                println("comp list fetched: ")
                println(comps)
                try {
                    compsDao.insertComps(comps.map { Competition(name = it, year = year) })
                    println("inserted comps for $year")
                    val stored = getCompetitions(year)
                    println(stored)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }

    private suspend fun syncComps() {
        withContext(Dispatchers.IO) {
            competitionYears.forEach {
                fetchAndStoreCompetitionsForYear(it)
            }
        }
    }


    //TODO
    suspend fun syncMeetings() {
        withContext(Dispatchers.IO) {
            val new = ktorClient.getMeetings(user)
            println("new: $new")
            new?.let { ls ->
                clearMeetingsCache()
                meetingDao.insertMeetings(ls.map {mtg -> MeetingEntity.fromShared(mtg, ktorClient.getUserById(mtg.createdBy, user)?.username?: mtg.createdBy) })
            }

//            val outdated = meetingDao.getOutdated(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli())
//            if (outdated.isNotEmpty()) {
//                meetingDao.deleteMeetings(outdated)
//            }
        }
    }

    private suspend fun clearMeetingsCache() {
        withContext(Dispatchers.IO) {
            meetingDao.deleteMeetings(meetingDao.getAll())
        }
    }

    suspend fun getMeetings(): List<Pair<Meeting, String?>> {
        return withContext(Dispatchers.IO) {
            meetingDao
                .getCurrent(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli())
                .map{ MeetingEntity.toShared(it) }
        }
    }

    suspend fun getOutdatedMeetings(): List<Pair<Meeting, String?>> {
        return if((this.user?.accountType?.value?:0) >= AccountType.ADMIN.value)
            withContext(Dispatchers.IO) {
            meetingDao
                .getOutdated(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli())
                .map{ MeetingEntity.toShared(it) }
        }
        else emptyList()
    }

    suspend fun createMeeting(
        type: String,
        description: String,
        startTime: Long,
        endTime: Long,
        value: Int,
        successCallback: (Meeting) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val meeting = ktorClient.createMeeting(
                user = user,
                type = type,
                description = description,
                startTime = startTime,
                endTime = endTime,
                value = value,
            )
            if (meeting != null) {
                successCallback(meeting)
                meetingDao.insertMeetings(listOf(MeetingEntity.fromShared(meeting, user?.username)))
            }
        }
    }

    suspend fun deleteMeeting(meetingId: String, callback: (String) -> Unit) {
        if(this.user?.isAdmin != true) {
            callback("You do not have permission to delete meetings")
            return
        }
        withContext(Dispatchers.IO) {
            if(ktorClient.deleteMeeting(meetingId, user)) {
                val mtg = meetingDao.getOne(meetingId)
                mtg?.let {meetingDao.deleteMeetings(listOf(it))}
                callback("Successfully deleted meeting")
            } else {
                callback("Failed to delete meeting")
            }
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
            comments = data.comments,
            storageType = StorageType.TEMP
        )
        var success = false
        val params = chargedUpParamsFromEntity(entity)
        withContext(Dispatchers.IO) {
            val res = ktorClient.submitChargedUp(params, user)
            println(res)
            success = res != null
            if (!success) try {
                chargedUpDao.insertChargedUp(entity)
                success = true
            } catch (e: Exception) {
                println(e)
            }
        }
        return success
    }

    private suspend fun syncChargedUp() {
        try{
            withContext(Dispatchers.IO) {
                val queue = chargedUpDao.getChargedUpTemp()
                queue.forEach {
                    val params = chargedUpParamsFromEntity(it)
                    val res = ktorClient.submitChargedUp(params, user)
                    if (res != null) {
                        chargedUpDao.deleteChargedUp(it)
                        println("UPLOADED $res")
                    }
                }
            }
        }catch(e: Exception) {
            println(e)
        }
    }

    private suspend fun clearChargedUpCache() {
        withContext(Dispatchers.IO) {
            chargedUpDao.deleteChargedUps(chargedUpDao.getAllChargedUps())
        }
    }

    private fun isOnline(): Boolean {
        val netInfo = this.connectivityManager?.getNetworkCapabilities(this.connectivityManager.activeNetwork)
        return netInfo != null && (netInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || netInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
    }

    fun sync(): Boolean {
        if(!isOnline()) return false
        println("Syncing")
        viewModelScope.launch {
            syncChargedUp()
            syncComps()
            syncUser()
        }
        return true
    }

    suspend fun deleteMe(password: String, callback: (Boolean, String) -> Unit) {
        withContext(Dispatchers.IO) {
            if(ktorClient.login(user?.username?: "", password) { _ -> } != null) {
                ktorClient.deleteMe(user) { b, s -> callback(b, s) }
            } else {
                callback(false, "Incorrect password")
            }
        }

    }

}