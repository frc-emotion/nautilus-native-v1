package org.team2658.nautilus

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.team2658.localstorage.AppDatabase
import org.team2658.localstorage.Competition
import org.team2658.localstorage.CrescendoDB
import org.team2658.localstorage.CrescendoUploadCacheDB
import org.team2658.localstorage.DatabaseDriverFactory
import org.team2658.localstorage.MeetingDB
import org.team2658.localstorage.SeasonsDB
import org.team2658.localstorage.UsersDB
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.attendance.UserAttendance
import org.team2658.nautilus.userauth.Subteam
import org.team2658.nautilus.userauth.User
import org.team2658.network.KtorError
import org.team2658.network.NetworkClient
import org.team2658.network.models.Season
import org.team2658.localstorage.Season as LocalSeason

class DataHandler(databaseDriverFactory: DatabaseDriverFactory, getToken: () -> String?, setToken: (String?) -> Unit) {
    private val db = AppDatabase(
        databaseDriverFactory.createDriver(),
        user_tableAdapter = org.team2658.localstorage.User_table.Adapter(accountTypeAdapter = IntColumnAdapter, gradeAdapter = IntColumnAdapter),
        competitionAdapter = Competition.Adapter(yearAdapter = IntColumnAdapter),
        seasonAdapter = LocalSeason.Adapter(yearAdapter = IntColumnAdapter)
    )
    private val usersDB = UsersDB(db)
    private val crescendoDB = CrescendoDB(db)
    private val crescendoUploadDB = CrescendoUploadCacheDB(db)
    private val attendanceUploadCache = db.attendanceLogsQueries
    private val meetingsDB = MeetingDB(db)
    private val seasonsDB = SeasonsDB(db)

    private val network = NetworkClient()

    private val scope = CoroutineScope(Dispatchers.IO)


    //object expressions for namespacing
    val users = object: UserNamspace {
        override suspend fun login(username: String, password: String, onError: (String) -> Unit): User? {
            return withContext(Dispatchers.IO){
                network.users.login(username, password, onError).let {
//                    usersDB.updateLoggedInUser(it, setToken)
                    when(it) {
                        is Result.Success -> usersDB.updateLoggedInUser(it.data, setToken)
                        is Result.Error -> null
                    }
                }
            }
        }

        override suspend fun register(
            username: String,
            password: String,
            email: String,
            firstName: String,
            lastName: String,
            subteam: Subteam,
            phone: String,
            grade: Int,
            errorCallback: (String) -> Unit
        ): User? {
            return withContext(Dispatchers.IO) {
                network.users.register(
                    username = username,
                    password = password,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    subteam = subteam,
                    phone = phone,
                    grade = grade,
                    errorCallback = errorCallback
                ).let {
                    when(it) {
                        is Result.Success -> usersDB.updateLoggedInUser(it.data, setToken)
                        is Result.Error -> null
                    }
                }
            }
        }

        override fun logout() {
            usersDB.logoutUser(setToken)
            clearAll()
            meetingsDB.deleteAll()
            attendanceUploadCache.clear()
            crescendoDB.deleteAll()
            crescendoUploadDB.deleteAll()
        }

        override suspend fun refreshLoggedIn(): User? {
            return withContext(Dispatchers.IO) {
                network.users.getMe(getToken()).let {
                    when(it) {
                        is Result.Success -> usersDB.updateLoggedInUser(it.data, setToken)
                        is Result.Error -> null
                    }
                }
            }
        }

        override fun loadLoggedIn(): User? = usersDB.getLoggedInUser(getToken()).also {
            println("token: ${it?.token}")
            println(getToken())
        }

        private suspend fun syncUsers(): List<User>? {
            return withContext(Dispatchers.IO) {
                network.users.getUsers(loadLoggedIn()).let {
                    when(it) {
                        is Result.Success -> {
                            usersDB.insertUsers(it.data)
                            usersDB.getUsers()
                        }
                        is Result.Error -> null
                    }
                }
            }
        }

        override fun loadAll(): List<User>? {
            return loadAll { _ -> }
        }

        override fun loadAll(onCompleteSync: (List<User>) -> Unit): List<User>? {
            return usersDB.getUsers().also {
                scope.launch {
                    syncUsers()?.let {
                        onCompleteSync(it)
                    }
                }
            }
        }

        override fun clearAll() = usersDB.clearUsers()

        override fun getMyAttendance(): List<UserAttendance>? = usersDB.getMyAttendance()

        override suspend fun sync() {
            syncUsers()
            refreshLoggedIn()
        }

        override fun deleteMe(onComplete: (Boolean, String?) -> Unit) {
            scope.launch {
                withContext(Dispatchers.IO) {
                    network.users.deleteMe(loadLoggedIn()) { s, m ->
                        if(s) {
                            logout()
                        }
                        onComplete(s, m)
                    }
                }
            }
        }
    }

    val attendance = object: AttendanceNamespace {
        override suspend fun sync() {
            _sync()?.let {
                meetingsDB.refresh(it)
            }
        }

        private suspend fun uploadCachedAttendance() {
            withContext(Dispatchers.IO) {
                attendanceUploadCache.get().executeAsList().forEach { mtg ->
                    network.attendance.attendMeeting(
                        user = users.loadLoggedIn(),
                        meetingId = mtg.meeting_id,
                        tapTime = mtg.tap_time,
                        verifiedBy = mtg.verifiedBy,
                        failureCallback = {}
                    ).let {
                        when (it) {
                            is Result.Success -> {
                                attendanceUploadCache.delete(it.data._id)
                            }
                            is Result.Error -> when(it.message) {
                                KtorError.AUTH, is KtorError.CLIENT-> attendanceUploadCache.delete(mtg.meeting_id)
                                KtorError.IO, is KtorError.SERVER -> Unit
                            }
                        }
                    }
                }
            }
        }

        private suspend fun _sync(): List<Meeting>? {
            return withContext(Dispatchers.IO) {
                network.attendance.getMeetings(users.loadLoggedIn()).let {
                    when(it) {
                        is Result.Success -> it.data
                        is Result.Error -> null
                    }
                }
            }.also {
                uploadCachedAttendance()
            }
        }

        override fun getAll(): List<Meeting> {
            return meetingsDB.getAll().also {
                scope.launch {
                    sync()
                }
            }
        }

        override fun getAll(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getAll().also {
                scope.launch {
                    sync()
                    onCompleteSync(meetingsDB.getAll())
                }
            }
        }

        override fun getCurrent(time: Long): List<Meeting> {
            return getCurrent(time) { _ -> }
        }

        override fun getCurrent(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getCurrent(time).also {
                scope.launch {
                    sync()
                    onCompleteSync(meetingsDB.getCurrent(time))
                }
            }
        }

        override fun getOutdated(time: Long): List<Meeting> {
            return getOutdated(time) { _ -> }
        }

        override fun getOutdated(
            time: Long,
            onCompleteSync: (List<Meeting>) -> Unit
        ): List<Meeting> {
            return meetingsDB.getOutdated(time).also {
                scope.launch {
                    sync()
                    onCompleteSync(meetingsDB.getOutdated(time))
                }
            }
        }

        override fun getArchived(): List<Meeting> {
            return getArchived { _ -> }
        }

        override fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getArchived().also {
                scope.launch {
                    sync()
                    onCompleteSync(meetingsDB.getArchived())
                }
            }
        }

        override fun archiveMeeting(id: String, onComplete: (Boolean) -> Unit) {
            scope.launch {
                network.attendance.archiveMeeting(id, users.loadLoggedIn()).let {
                    when(it) {
                        is Result.Success -> {
                            sync()
                            onComplete(true)
                        }
                        is Result.Error -> onComplete(false)
                    }
                }
            }
        }

        override fun clear() {
            meetingsDB.deleteAll()
        }

        override fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
            onError: (String) -> Unit,
            onSuccess: () -> Unit
        ) {
            scope.launch {
                withContext(Dispatchers.IO){
                    network.attendance.createMeeting(
                        user = users.loadLoggedIn(),
                        startTime = startTime,
                        endTime = endTime,
                        type = type,
                        description = description,
                        value = value,
                        attendancePeriod = attendancePeriod,
                        errorCallback = onError,
                    ).let{
//                        meetingsDB.insert(it)
//                        onSuccess()
                        when(it) {
                            is Result.Success -> {
                                meetingsDB.insert(it.data)
                                onSuccess()
                            }
                            is Result.Error -> {}
                        }
                    }
                }
            }
        }

        override fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String,
            onError: (String) -> Unit,
            onSuccess: () -> Unit
        ) {
            scope.launch {
                withContext(Dispatchers.IO){
                    network.attendance.attendMeeting(
                        user = users.loadLoggedIn(),
                        meetingId = meetingId,
                        tapTime = time,
                        failureCallback = onError,
                        verifiedBy = verifiedBy
                    ).let {
//                        usersDB.updateLoggedInUser(it, setToken)
//                        onSuccess()
                        when(it) {
                            is Result.Success -> {
                                usersDB.updateLoggedInUser(it.data, setToken)
                                onSuccess()
                            }

                            is Result.Error -> when (it.message) {
                                KtorError.AUTH -> {}
                                is KtorError.CLIENT -> {}
                                KtorError.IO -> {
                                    attendanceUploadCache.insert(meetingId, time, verifiedBy)
                                }
                                is KtorError.SERVER -> {}
                            }
                        }
                    }
                }
            }
        }

        override suspend fun delete(id: String): Boolean {
            return withContext(Dispatchers.IO) {
                network.attendance.deleteMeeting(user = users.loadLoggedIn(), id = id).let {
                    when(it) {
                        is Result.Success -> {
                            meetingsDB.delete(id)
                            true
                        }
                        is Result.Error -> false
                    }
                }
            }
        }
    }

    val seasons = object: SeasonsNamespace {
        override suspend fun sync() {
            _sync()?.let {
                seasonsDB.deleteAll()
                seasonsDB.insert(it)
            }
        }

        suspend fun _sync(): List<Season>? {
            return withContext(Dispatchers.IO) {
                network.getSeasons().let {
                    when(it) {
                        is Result.Success -> it.data
                        is Result.Error -> null
                    }
                }
            }
        }

        override fun getComps(year: Int): List<String> {
            return seasonsDB.getCompsFromSeason(year).also {
                scope.launch {
                    sync()
                }
            }
        }

        override fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String> {
            return seasonsDB.getCompsFromSeason(year).also {
                scope.launch {
                    sync()
                    onCompleteSync(seasonsDB.getCompsFromSeason(year))
                }
            }
        }

        override fun getAttendancePeriods(): Map<Int, List<String>> {
            return seasonsDB.getAttendancePeriods().also {
                scope.launch {
                    sync()
                }
            }
        }

        override fun getAttendancePeriods(onCompleteSync: (Map<Int, List<String>>) -> Unit): Map<Int, List<String>> {
            return seasonsDB.getAttendancePeriods().also {
                scope.launch {
                    sync()
                    onCompleteSync(seasonsDB.getAttendancePeriods())
                }
            }
        }

    }

    fun getQueueLength(): Long {
        return attendanceUploadCache.getCacheLength().executeAsOneOrNull() ?: 0
    }
    fun sync() {
        scope.launch {
            users.sync()
            attendance.sync()
            seasons.sync()
        }
    }

    suspend fun syncCoroutine() {
        users.sync()
        attendance.sync()
        seasons.sync()
    }

    fun getNetworkClient() = network
    //interfaces are required for methods of namespace objects to be accessible
    interface UserNamspace {
        suspend fun login(username: String, password: String, onError: (String) -> Unit): User?
        suspend fun register(
            username: String,
            password: String,
            email: String,
            firstName: String,
            lastName: String,
            subteam: Subteam,
            phone: String,
            grade: Int,
            errorCallback: (String) -> Unit = {}
        ): User?
        fun logout()
        suspend fun refreshLoggedIn(): User?
        fun loadLoggedIn(): User?
        fun loadAll(): List<User>?
        fun loadAll(onCompleteSync: (List<User>) -> Unit): List<User>?
        fun clearAll()
        suspend fun sync()
        fun getMyAttendance(): List<UserAttendance>?
        fun deleteMe(onComplete: (Boolean, String?) -> Unit)
    }
    interface AttendanceNamespace {
        suspend fun sync()

        fun getAll(): List<Meeting>

        fun getAll(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getCurrent(time: Long): List<Meeting>

        fun getCurrent(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getOutdated(time: Long): List<Meeting>

        fun getOutdated(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getArchived(): List<Meeting>

        fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun archiveMeeting(id: String, onComplete: (Boolean) -> Unit)

        fun clear()

        suspend fun delete(id: String): Boolean //true = success

        fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
            onError: (String) -> Unit,
            onSuccess: () -> Unit
        )

        fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String,
            onError: (String) -> Unit,
            onSuccess: () -> Unit,
        )

    }
    interface SeasonsNamespace {
        suspend fun sync()
        fun getComps(year: Int): List<String>
        fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String>
        fun getAttendancePeriods(): Map<Int, List<String>>
        fun getAttendancePeriods(onCompleteSync: (Map<Int, List<String>>) -> Unit): Map<Int, List<String>>
    }
}