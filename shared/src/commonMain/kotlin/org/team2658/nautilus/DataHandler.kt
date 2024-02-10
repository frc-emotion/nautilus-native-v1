package org.team2658.nautilus

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.team2658.localstorage.AppDatabase
import org.team2658.localstorage.Attendance_periods_table
import org.team2658.localstorage.Attendance_table
import org.team2658.localstorage.Competition_table
import org.team2658.localstorage.CrescendoDB
import org.team2658.localstorage.CrescendoUploadCacheDB
import org.team2658.localstorage.Crescendo_table
import org.team2658.localstorage.DatabaseDriverFactory
import org.team2658.localstorage.MeetingDB
import org.team2658.localstorage.Season_table
import org.team2658.localstorage.SeasonsDB
import org.team2658.localstorage.User_extras_table
import org.team2658.localstorage.UsersDB
import org.team2658.localstorage.uploadcache.Crescendo_upload_table
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.scouting.scoutingdata.Crescendo
import org.team2658.nautilus.scouting.scoutingdata.CrescendoRequestBody
import org.team2658.nautilus.userauth.Subteam
import org.team2658.nautilus.userauth.TokenUser
import org.team2658.nautilus.userauth.User
import org.team2658.network.KtorError
import org.team2658.network.NetworkClient
import org.team2658.network.models.Season

class DataHandler(routeBase: String, databaseDriverFactory: DatabaseDriverFactory, getToken: () -> String?, setToken: (String?) -> Unit) {
    private val db = AppDatabase(
        databaseDriverFactory.createDriver(),
        user_tableAdapter = org.team2658.localstorage.User_table.Adapter(accountTypeAdapter = IntColumnAdapter),
        competition_tableAdapter = Competition_table.Adapter(yearAdapter = IntColumnAdapter),
        season_tableAdapter = Season_table.Adapter(yearAdapter = IntColumnAdapter),
        attendance_periods_tableAdapter = Attendance_periods_table.Adapter(yearAdapter = IntColumnAdapter),
        attendance_tableAdapter = Attendance_table.Adapter(IntColumnAdapter),
        crescendo_tableAdapter = Crescendo_table.Adapter(
            auto_ampNotesAdapter = IntColumnAdapter,
            auto_speakerNotesAdapter = IntColumnAdapter,
            matchNumberAdapter = IntColumnAdapter,
            penaltyAdapter = IntColumnAdapter,
            harmonyAdapter = IntColumnAdapter,
            rankingPointsAdapter = IntColumnAdapter,
            scoreAdapter = IntColumnAdapter,
            teamNumberAdapter = IntColumnAdapter,
            teleop_ampNotesAdapter = IntColumnAdapter,
            teleop_speakerAmpedAdapter = IntColumnAdapter,
            teleop_speakerUnampedAdapter = IntColumnAdapter,
            trapNotesAdapter = IntColumnAdapter,
        ),
        crescendo_upload_tableAdapter = Crescendo_upload_table.Adapter(
            scoreAdapter = IntColumnAdapter,
            autoAmpAdapter = IntColumnAdapter,
            teamNumberAdapter = IntColumnAdapter,
            rankingPointsAdapter = IntColumnAdapter,
            harmonyAdapter = IntColumnAdapter,
            teleopAmpAdapter = IntColumnAdapter,
            autoSpeakerAdapter = IntColumnAdapter,
            matchNumberAdapter = IntColumnAdapter,
            penaltyAdapter = IntColumnAdapter,
            teleopSpeakerAmpAdapter = IntColumnAdapter,
            teleopSpeakerUnampAdapter = IntColumnAdapter,
            trapNotesAdapter = IntColumnAdapter,
        ),
        user_extras_tableAdapter = User_extras_table.Adapter(gradeAdapter = IntColumnAdapter, accountUpdateVersionAdapter = IntColumnAdapter)
    )

    private val usersDB = UsersDB(db)
    private val crescendoDB = CrescendoDB(db)
    private val crescendoUploadDB = CrescendoUploadCacheDB(db)
    private val attendanceUploadCache = db.attendanceLogsQueries
    private val meetingsDB = MeetingDB(db)
    private val seasonsDB = SeasonsDB(db)

    private val network = NetworkClient(routeBase)

    private val scope = CoroutineScope(Dispatchers.IO)
    private val user: TokenUser?
        get() = Result.unwrapOrNull(users.loadLoggedIn())


    //object expressions for namespacing
    val users = object: UserNamspace {
        override suspend fun login(username: String, password: String): DataResult<TokenUser> {
            return withContext(Dispatchers.IO){
                when(val result = network.users.login(username, password)) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, setToken)
                        Result.Success(result.data)
                    }
                    is Result.Error -> when(val error = result.error) {
                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                        is KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
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
            grade: Int
        ): DataResult<TokenUser> {
            return withContext(Dispatchers.IO) {
                val result = network.users.register(
                    username = username,
                    password = password,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    subteam = subteam,
                    phone = phone,
                    grade = grade
                )
                when(result) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, setToken)
                        Result.Success(result.data)
                    }
                    is Result.Error -> when(val error = result.error) {
                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                        is KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
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

        override suspend fun refreshLoggedIn(): DataResult<TokenUser> {
            return withContext(Dispatchers.IO) {
                when(val result = network.users.getMe(getToken())) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, setToken)
                        Result.Success(result.data)
                    }
                    is Result.Error -> when(val error = result.error) {
                        is KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                        is KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                    }
                }
            }
        }

        override fun loadLoggedIn(): DataResult<TokenUser> {
            val token = getToken() ?: return Result.Error("Authentication error. Please log out and log back in.")
            return usersDB.getLoggedInUser(token).let {
                if (it == null) {
                    Result.Error("Authentication error. Please log out and log back in.")
                } else {
                    Result.Success(it)
                }
            }
        }

        private suspend fun syncUsers(): DataResult<List<User.WithoutToken>> {
            return withContext(Dispatchers.IO) {
                network.users.getUsers(user ?: return@withContext Result.Error("Authentication error. Please log out and log back in.")).let {
                    when(it) {
                        is Result.Success -> {
                            usersDB.insertUsers(it.data)
                            Result.Success(it.data)
                        }
                        is Result.Error -> when(val error = it.error) {
                            is KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
            }
        }

        override fun loadAll(): List<User.WithoutToken> {
            return loadAll { _ -> }
        }

        override fun loadAll(onCompleteSync: (List<User.WithoutToken>) -> Unit): List<User.WithoutToken> {
            scope.launch {
                syncUsers().let {
                    if(it is Result.Success) onCompleteSync(it.data)
                }
            }
            return usersDB.getUsers()
        }

        override fun clearAll() = usersDB.clearUsers()

        override suspend fun sync(): SyncUserResult {
            val listRes = syncUsers()
            val myRes = refreshLoggedIn()
            return SyncUserResult(myUser = myRes, userList = listRes)
        }

        override suspend fun deleteMe(): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                when(val res = network.users.deleteMe(user ?: return@withContext Result.Error("Authentication error. Please log out and log back in."))) {
                    is Result.Success -> {
                        logout()
                        Result.Success(Unit)
                    }
                    is Result.Error -> when(val error = res.error) {
                        is KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                        is KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                    }
                }
            }
        }
    }

    val attendance = object: AttendanceNamespace {
        override suspend fun sync(): UploadDownloadResult<List<DataResult<TokenUser>>, DataResult<List<Meeting>>>{
            return withContext(Dispatchers.IO) {
                val usr = user
                val downloadRes = if(usr == null) Result.Error("Authentication error. Please log out and log back in.") else network.attendance.getMeetings(usr).let {
                    when(it) {
                        is Result.Success -> it.also {
                            meetingsDB.refresh(it.data)
                        }
                        is Result.Error -> when(val error = it.error) {
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
                val uploadRes = uploadCachedAttendance()
                UploadDownloadResult(upload = uploadRes, download = downloadRes)
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
                    sync().let {
                        if(it.download is Result.Success) onCompleteSync(meetingsDB.getAll())
                    }
                }
            }
        }

        override fun getCurrent(time: Long): List<Meeting> {
            return getCurrent(time) { _ -> }
        }

        override fun getCurrent(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getCurrent(time).also {
                scope.launch {
                    sync().let {
                       if(it.download is Result.Success) onCompleteSync(meetingsDB.getCurrent(time))
                    }

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
                    sync().let {
                        if(it.download is Result.Success) onCompleteSync(meetingsDB.getOutdated(time))
                    }
                }
            }
        }

        override fun getArchived(): List<Meeting> {
            return getArchived { _ -> }
        }

        override fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getArchived().also {
                scope.launch {
                    sync().let {
                        if(it.download is Result.Success) onCompleteSync(meetingsDB.getArchived())
                    }
                }
            }
        }

        override suspend fun archiveMeeting(id: String): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                network.attendance.archiveMeeting(id, user ?: return@withContext Result.Error("Authentication error. Please log out and log back in.")).let {
                    when(it) {
                        is Result.Success -> {
                            sync()
                            Result.Success(Unit)
                        }
                        is Result.Error -> when(val error = it.error) {
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
            }
        }

        override fun clear() {
            meetingsDB.deleteAll()
        }

        override suspend fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
        ): DataResult<Meeting> {
           if(description.isBlank() || type.isBlank() || attendancePeriod.isBlank()) {
               return Result.Error("Invalid input")
           }
            return withContext(Dispatchers.IO){
                network.attendance.createMeeting(
                    user = user ?: return@withContext Result.Error("Authentication error. Please log out and log back in."),
                    startTime = startTime,
                    endTime = endTime,
                    type = type,
                    description = description,
                    value = value,
                    attendancePeriod = attendancePeriod,
                ).let {
                    when(it) {
                        is Result.Success -> {
                            meetingsDB.insert(it.data)
                            Result.Success(it.data)
                        }
                        is Result.Error -> when(val error = it.error) {
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
            }
        }

        override suspend fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String
        ): DataResult<TokenUser> {
            return withContext(Dispatchers.IO){
                network.attendance.attendMeeting(
                    user = user ?: return@withContext Result.Error("Authentication error. Please log out and log back in."),
                    meetingId = meetingId,
                    tapTime = time,
                    verifiedBy = verifiedBy
                ).let {
                    when(it) {
                        is Result.Success -> {
                            usersDB.updateLoggedInUser(it.data, setToken)
                            Result.Success(it.data)
                        }
                        is Result.Error -> when (val error = it.error) {
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in, then scan again.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> {
                                attendanceUploadCache.insert(meetingId, time, verifiedBy) //cache data to retry later when there is internet
                                Result.Error("IO error: Attendance will be uploaded when you are connected to the internet.")
                            }
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}").also {
                                attendanceUploadCache.insert(meetingId, time, verifiedBy) //cache data to retry later
                            }
                        }
                    }
                }
            }
        }

        override suspend fun delete(id: String): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                network.attendance.deleteMeeting(user = user ?: return@withContext Result.Error("Authentication error. Please log out and log back in."), id = id).let {
                    when(it) {
                        is Result.Success -> {
                            meetingsDB.delete(id)
                            Result.Success(Unit)
                        }
                        is Result.Error -> when(val error = it.error) {
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
            }
        }

        private suspend fun uploadCachedAttendance(): List<DataResult<TokenUser>> {
            return withContext(Dispatchers.IO) {
                attendanceUploadCache.get().executeAsList().map { mtg ->
                    network.attendance.attendMeeting(
                        user = user ?: return@map Result.Error("Authentication error. Please log out and log back in."),
                        meetingId = mtg.meeting_id,
                        tapTime = mtg.tap_time,
                        verifiedBy = mtg.verifiedBy
                    ).let {
                        when (it) {
                            is Result.Success -> it.also {
                                attendanceUploadCache.delete(it.data._id)
                            }
                            is Result.Error -> when(val error = it.error) {
                                KtorError.AUTH -> Result.Error("Authentication Error").also { attendanceUploadCache.delete(mtg.meeting_id) }
                                is KtorError.CLIENT-> Result.Error("Error ${error.code}: ${error.message}").also { attendanceUploadCache.delete(mtg.meeting_id) }
                                KtorError.IO -> Result.Error("IO error: Attendance will be uploaded when you are connected to the internet.")
                                is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                            }
                        }
                    }
                }.also { users.sync() }
            }
        }

    }

    val seasons = object: SeasonsNamespace {
        override suspend fun sync(): DataResult<List<Season>> {
            return withContext(Dispatchers.IO) {
                network.getSeasons().let {
                    when(it) {
                        is Result.Success -> it.also {
                            seasonsDB.deleteAll()
                            seasonsDB.insert(it.data)
                        }
                        is Result.Error -> when(val error = it.error) {
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        }
                    }
                }
            }
        }

        override fun getComps(year: Int): List<String> {
            return getComps(year) { _ -> }
        }

        override fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String> {
            return seasonsDB.getCompsFromSeason(year).also {
                scope.launch {
                    sync().let {
                        if(it is Result.Success) onCompleteSync(seasonsDB.getCompsFromSeason(year))
                    }
                }
            }
        }

        override fun getAttendancePeriods(): List<String> {
            return getAttendancePeriods { _ -> }
        }

        override fun getAttendancePeriods(onCompleteSync: (List<String>) -> Unit): List<String> {
            return seasonsDB.getAttendancePeriods().also {
                scope.launch {
                    sync().let {
                        if(it is Result.Success) onCompleteSync(seasonsDB.getAttendancePeriods())
                    }
                }
            }
        }
    }

    val crescendo = object: CrescendoNamespace {

        override suspend fun sync(): UploadDownloadResult<List<DataResult<Crescendo>>, DataResult<List<Crescendo>>> {
         return withContext(Dispatchers.IO) {
             val usr = user   
             val downloadRes = if(usr == null) Result.Error("Authentication error. Please log out and log back in.") else network.crescendo.getCrescendos(usr).let {
                    when(it) {
                        is Result.Success -> {
                            crescendoDB.deleteAll()
                            crescendoDB.insert(it.data)
                            it
                        }
                        is Result.Error -> when(val error = it.error) {
                            is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                            KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
                            is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                            KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                        }
                    }
                }
                val uploadRes =  crescendoUploadDB.getAll().map { (id, data) ->
                    network.crescendo.uploadCrescendo(user ?: return@map Result.Error("Authentication error. Please log out and log back in."), data).let {
                        when(it) {
                            is Result.Success -> {
                                crescendoUploadDB.delete(id)
                                it
                            }
                            is Result.Error -> when(val error = it.error) {
                                is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                                KtorError.IO -> Result.Error("IO error. Your scouting data has been saved offline. Please connect to the internet and try again to upload your data.")
                                is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                                KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                            }
                        }
                    }
                }
                UploadDownloadResult(upload = uploadRes, download = downloadRes)
            }
        }

        override fun getAll(): List<Crescendo> {
            return getAll { _ -> }
        }

        override fun getAll(onCompleteSync: (List<Crescendo>) -> Unit): List<Crescendo> {
            scope.launch {
                sync().let {
                    if(it.download is Result.Success) onCompleteSync(crescendoDB.getAll())
                }
            }
            return crescendoDB.getAll()
        }

        override fun clearDownloads() {
            crescendoDB.deleteAll()
        }

//        override suspend fun delete(id: String): DataResult<Unit> {
//            withContext(Dispatchers.IO) {
//                when(val res = network.crescendo.del(user, id)) {
//                    is Result.Success -> {
//                        crescendoDB.delete(id)
//                        Result.Success(Unit)
//                    }
//                    is Result.Error -> when(val error = res.error) {
//                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
//                        KtorError.IO -> Result.Error("IO error. Please make sure you are connected to the internet.")
//                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
//                        KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
//                    }
//                }
//            }
//        }

        override suspend fun upload(data: CrescendoRequestBody): DataResult<Crescendo> {
            return withContext(Dispatchers.IO) {
                when(val res = network.crescendo.uploadCrescendo(user ?: return@withContext Result.Error("Authentication error. Please log out and log back in."), data)) {
                    is Result.Success -> res.also { crescendoDB.insert(it.data) }
                    is Result.Error -> when(val error = res.error) {
                        is KtorError.CLIENT -> Result.Error("Error ${error.code}: ${error.message}")
                        KtorError.IO -> {
                            crescendoUploadDB.insert(data)
                            Result.Error("IO error. Your scouting data has been saved offline. Please connect to the internet and go to settings to upload your data.")
                        }
                        is KtorError.SERVER -> Result.Error("Server error ${error.code}: ${error.message}")
                        KtorError.AUTH -> Result.Error("Authentication error: Your login session is invalid. Please log out and log back in.")
                    }
                }
            }
        }

    }

    fun getQueueLength(): UploadQueueLength {
        val attendance = attendanceUploadCache.getCacheLength().executeAsOneOrNull() ?: 0L
        val crescendo = db.crescendoUploadCacheQueries.getLength().executeAsOneOrNull() ?: 0L
        return UploadQueueLength(attendance = attendance, crescendo = crescendo)
    }

    fun sync() {
        scope.launch {
            syncCoroutine()
        }
    }

    suspend fun syncCoroutine(): SyncResult {
        users.sync()
        attendance.sync()
        seasons.sync()
        crescendo.sync()
        return SyncResult(
            user = users.sync(),
            attendance = attendance.sync(),
            seasons = seasons.sync(),
            crescendo = crescendo.sync()
        )
    }

    fun getNetworkClient() = network

    //interfaces are required for methods of namespace objects to be accessible,
    //   without making them implement an interface you just get an error that those methods
    //   don't exist on the type of the object
    interface UserNamspace {
        suspend fun login(username: String, password: String): DataResult<TokenUser>
        suspend fun register(
            username: String,
            password: String,
            email: String,
            firstName: String,
            lastName: String,
            subteam: Subteam,
            phone: String,
            grade: Int,
        ): DataResult<TokenUser>

        fun logout()
        suspend fun refreshLoggedIn(): DataResult<TokenUser>
        fun loadLoggedIn(): DataResult<TokenUser>
        fun loadAll(): List<User.WithoutToken>
        fun loadAll(onCompleteSync: (List<User.WithoutToken>) -> Unit): List<User.WithoutToken>
        fun clearAll()
        suspend fun sync(): SyncUserResult
        suspend fun deleteMe(): DataResult<Unit>
    }
    interface AttendanceNamespace {
        suspend fun sync(): UploadDownloadResult<List<DataResult<TokenUser>>, DataResult<List<Meeting>>>

        fun getAll(): List<Meeting>

        fun getAll(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getCurrent(time: Long): List<Meeting>

        fun getCurrent(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getOutdated(time: Long): List<Meeting>

        fun getOutdated(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getArchived(): List<Meeting>

        fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        suspend fun archiveMeeting(id: String): DataResult<Unit>

        fun clear()

        suspend fun delete(id: String): DataResult<Unit> //true = success

        suspend fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
        ): DataResult<Meeting>

        suspend fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String,
        ): DataResult<TokenUser>

    }
    interface SeasonsNamespace {
        suspend fun sync(): DataResult<List<Season>>
        fun getComps(year: Int): List<String>
        fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String>
        fun getAttendancePeriods(): List<String>
        fun getAttendancePeriods(onCompleteSync: (List<String>) -> Unit): List<String>
    }

    interface CrescendoNamespace {
        suspend fun sync(): UploadDownloadResult<List<DataResult<Crescendo>>, DataResult<List<Crescendo>>>
        fun getAll(): List<Crescendo>
        fun getAll(onCompleteSync: (List<Crescendo>) -> Unit): List<Crescendo>
        fun clearDownloads()
//        suspend fun delete(id: String): DataResult<Unit>
        suspend fun upload(data: CrescendoRequestBody): DataResult<Crescendo>
    }
}


typealias DataResult<T> = Result<T, String>

data class SyncUserResult(val myUser: DataResult<TokenUser>, val userList: DataResult<List<User.WithoutToken>>)

data class UploadDownloadResult<T, U>(val upload: T, val download: U)

data class UploadQueueLength(val attendance: Long, val crescendo: Long)

data class SyncResult (
    val user: SyncUserResult,
    val attendance: UploadDownloadResult<List<DataResult<TokenUser>>, DataResult<List<Meeting>>>,
    val seasons: DataResult<List<Season>>,
    val crescendo: UploadDownloadResult<List<DataResult<Crescendo>>, DataResult<List<Crescendo>>>
)