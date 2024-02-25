@file:Suppress("unused")

package org.nautilusapp.nautilus

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.nautilusapp.localstorage.AppDatabase
import org.nautilusapp.localstorage.Attendance_periods_table
import org.nautilusapp.localstorage.Attendance_table
import org.nautilusapp.localstorage.Competition_table
import org.nautilusapp.localstorage.CrescendoDB
import org.nautilusapp.localstorage.CrescendoUploadCacheDB
import org.nautilusapp.localstorage.Crescendo_table
import org.nautilusapp.localstorage.DatabaseDriverFactory
import org.nautilusapp.localstorage.MeetingDB
import org.nautilusapp.localstorage.Season_table
import org.nautilusapp.localstorage.SeasonsDB
import org.nautilusapp.localstorage.User_extras_table
import org.nautilusapp.localstorage.UsersDB
import org.nautilusapp.localstorage.uploadcache.Crescendo_upload_table
import org.nautilusapp.nautilus.attendance.Meeting
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoSubmission
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.TokenUser
import org.nautilusapp.nautilus.userauth.User
import org.nautilusapp.nautilus.validation.CreateMeetingArgs
import org.nautilusapp.nautilus.validation.createMeetingValidArgs
import org.nautilusapp.network.KtorError
import org.nautilusapp.network.NetworkClient
import org.nautilusapp.network.models.Season
import org.nautilusapp.openapi.MANIFEST
import org.nautilusapp.openapi.manifestCompat

class DataHandler(
    routeBase: String,
    databaseDriverFactory: DatabaseDriverFactory,
    private val getToken: () -> String?,
    private val setToken: (String?) -> Unit
) {
    private val db = AppDatabase(
        databaseDriverFactory.createDriver(),
        user_tableAdapter = org.nautilusapp.localstorage.User_table.Adapter(accountTypeAdapter = IntColumnAdapter),
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
        user_extras_tableAdapter = User_extras_table.Adapter(
            gradeAdapter = IntColumnAdapter,
            accountUpdateVersionAdapter = IntColumnAdapter
        )
    )

    private val usersDB = UsersDB(db)
    private val crescendoDB = CrescendoDB(db)
    private val crescendoUploadDB = CrescendoUploadCacheDB(db)
    private val attendanceUploadCache = db.attendanceLogsQueries
    private val meetingsDB = MeetingDB(db)
    private val seasonsDB = SeasonsDB(db)

    private val network = NetworkClient(routeBase)

    private val scope = CoroutineScope(Dispatchers.IO)

    private fun user(): TokenUser? {
        val token = getToken()
        return usersDB.getLoggedInUser(token ?: return null)
    }

    private fun _setToken(token: String?) {
        setToken(token)
    }

    /**
     * Get OpenAPI manifest from server and compare it to the local manifest
     * @return **true** if the server manifest matches the local manifest or if there is an error, **false** if it does not
     */
    suspend fun manifestOk(): Boolean {
//        val manifest = Result.unwrapOrNull(network.getAppManifest()) ?: return true
        val manifest = network.getAppManifest().unwrapOrNull() ?: return true
        return manifestCompat(
            compare = MANIFEST,
            manifest = manifest,
            usedPaths = network.relativepaths.values.toList()
        )
    }

    //object expressions for namespacing
    val users = object : UserNamspace {
        override suspend fun login(username: String, password: String): DataResult<TokenUser> {
            return withContext(Dispatchers.IO) {
                when (val result = network.users.login(username, password)) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, ::_setToken)
                        Result.Success(result.data)
                    }

                    is Result.Error -> when (val error = result.error) {
                        is KtorError.SERVER -> Result.Error(
                            Error(
                                "Server error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.CLIENT -> Result.Error(
                            Error(
                                "Error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
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
                when (result) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, ::_setToken)
                        Result.Success(result.data)
                    }

                    is Result.Error -> when (val error = result.error) {
                        is KtorError.SERVER -> Result.Error(
                            Error(
                                "Server error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.CLIENT -> Result.Error(
                            Error(
                                "Error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                    }
                }
            }
        }

        override fun logout() {
            usersDB.logoutUser(::_setToken)
            usersDB.deleteAll()
            meetingsDB.deleteAll()
            attendanceUploadCache.clear()
            crescendoDB.deleteAll()
            crescendoUploadDB.deleteAll()
            seasonsDB.deleteAll()
        }

        override suspend fun refreshLoggedIn(): DataResult<TokenUser> {
            return withContext(Dispatchers.IO) {
                when (val result = network.users.getMe(getToken())) {
                    is Result.Success -> {
                        usersDB.updateLoggedInUser(result.data, ::_setToken)
                        Result.Success(result.data)
                    }

                    is Result.Error -> when (val error = result.error) {
                        is KtorError.AUTH -> Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )

                        is KtorError.CLIENT -> Result.Error(
                            Error(
                                "Error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                        is KtorError.SERVER -> Result.Error(
                            Error(
                                "Server error ${error.code}: ${error.message}",
                                error.code
                            )
                        )
                    }
                }
            }
        }


        override fun loadLoggedIn(): DataResult<TokenUser> {
            val token = getToken()
                ?: return Result.Error(
                    Error(
                        "Authentication error: Your login session is invalid. Please log out and log back in.",
                        401
                    )
                )
            return usersDB.getLoggedInUser(token).let {
                if (it == null) {
                    Result.Error(
                        Error(
                            "Authentication error: Your login session is invalid. Please log out and log back in.",
                            401
                        )
                    )
                } else {
                    Result.Success(it)
                }
            }
        }


        private suspend fun syncUsers(): DataResult<List<User.WithoutToken>> {
            return withContext(Dispatchers.IO) {
                network.users.getUsers(
                    user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )
                ).let {
                    when (it) {
                        is Result.Success -> {
                            usersDB.insertUsers(it.data)
                            Result.Success(it.data)
                        }

                        is Result.Error -> when (val error = it.error) {
                            is KtorError.AUTH -> Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            )

                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
                        }
                    }
                }
            }
        }

        override fun loadAll(): List<User.WithoutToken> {
            return usersDB.getUsers()
        }

        override fun loadAll(onCompleteSync: (List<User.WithoutToken>) -> Unit): List<User.WithoutToken> {
            scope.launch {
                syncUsers().let {
                    if (it is Result.Success) onCompleteSync(it.data)
                }
            }
            return usersDB.getUsers()
        }

        override fun clearAll() = usersDB.clearUsers()

        override suspend fun sync(): SyncUserResult {
            val listRes = scope.async {
                syncUsers()
            }
            val myRes = scope.async { refreshLoggedIn() }
            return SyncUserResult(myUser = myRes.await(), userList = listRes.await())
        }

        override suspend fun deleteMe(): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                when (val res = network.users.deleteMe(
                    user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )
                )) {
                    is Result.Success -> {
                        logout()
                        Result.Success(Unit)
                    }

                    is Result.Error -> when (val error = res.error) {
                        is KtorError.AUTH -> Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )

                        is KtorError.CLIENT -> Result.Error(
                            Error(
                                "Error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        is KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                        is KtorError.SERVER -> Result.Error(
                            Error(
                                "Server error ${error.code}: ${error.message}",
                                error.code
                            )
                        )
                    }
                }
            }
        }
    }

    val attendance = object : AttendanceNamespace {
        override suspend fun sync(): AttendanceResult {
            return withContext(Dispatchers.IO) {
                val usr = user()
                val downloadRes = scope.async {
                    if (usr == null) Result.Error(
                        Error(
                            "Authentication error: Your login session is invalid. Please log out and log back in.",
                            401
                        )
                    ) else network.attendance.getMeetings(
                        usr
                    ).let {
                        when (it) {
                            is Result.Success -> it.also {
                                meetingsDB.refresh(it.data)
                            }

                            is Result.Error -> when (val error = it.error) {
                                KtorError.AUTH -> Result.Error(
                                    Error(
                                        "Authentication error: Your login session is invalid. Please log out and log back in.",
                                        401
                                    )
                                )

                                is KtorError.CLIENT -> Result.Error(
                                    Error(
                                        "Error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )

                                KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                                is KtorError.SERVER -> Result.Error(
                                    Error(
                                        "Server error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )
                            }
                        }
                    }
                }
                val uploadRes = async {
                    uploadCachedAttendance()
                }
                AttendanceResult(upload = uploadRes.await(), download = downloadRes.await())
            }
        }


        override fun getAll(): List<Meeting> {
            return meetingsDB.getAll()
        }

        override fun getAll(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getAll().also {
                scope.launch {
                    sync().let {
                        if (it.download is Result.Success) onCompleteSync(meetingsDB.getAll())
                    }
                }
            }
        }

        override fun getCurrent(time: Long): List<Meeting> {
            return meetingsDB.getCurrent(time)
        }

        override fun getCurrent(
            time: Long,
            onCompleteSync: (List<Meeting>) -> Unit
        ): List<Meeting> {
            return meetingsDB.getCurrent(time).also {
                scope.launch {
                    sync().let {
                        if (it.download is Result.Success) onCompleteSync(meetingsDB.getCurrent(time))
                    }

                }
            }
        }

        override fun getOutdated(time: Long): List<Meeting> {
            return meetingsDB.getOutdated(time)
        }

        override fun getOutdated(
            time: Long,
            onCompleteSync: (List<Meeting>) -> Unit
        ): List<Meeting> {
            return meetingsDB.getOutdated(time).also {
                scope.launch {
                    sync().let {
                        if (it.download is Result.Success) onCompleteSync(
                            meetingsDB.getOutdated(
                                time
                            )
                        )
                    }
                }
            }
        }

        override fun getArchived(): List<Meeting> {
            return meetingsDB.getArchived()
        }

        override fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting> {
            return meetingsDB.getArchived().also {
                scope.launch {
                    sync().let {
                        if (it.download is Result.Success) onCompleteSync(meetingsDB.getArchived())
                    }
                }
            }
        }

        override suspend fun archiveMeeting(id: String): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                network.attendance.archiveMeeting(
                    id,
                    user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )
                ).let {
                    when (it) {
                        is Result.Success -> {
                            sync()
                            Result.Success(Unit)
                        }

                        is Result.Error -> when (val error = it.error) {
                            KtorError.AUTH -> Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            )

                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
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
            val validate = createMeetingValidArgs(
                CreateMeetingArgs(
                    type = type,
                    description = description,
                    startTimeMs = startTime,
                    endTimeMs = endTime,
                    meetingValue = value,
                    attendancePeriod = attendancePeriod
                )
            )
            if (!validate.ok) {
                return Result.Error(
                    Error(
                        "Invalid input: ${validate.reasons.joinToString(", ")}",
                        400
                    )
                )
            }
            return withContext(Dispatchers.IO) {
                network.attendance.createMeeting(
                    user = user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        ),
                    startTime = startTime,
                    endTime = endTime,
                    type = type,
                    description = description,
                    value = value,
                    attendancePeriod = attendancePeriod,
                ).let {
                    when (it) {
                        is Result.Success -> {
                            meetingsDB.insert(it.data)
                            Result.Success(it.data)
                        }

                        is Result.Error -> when (val error = it.error) {
                            KtorError.AUTH -> Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            )

                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
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
            return withContext(Dispatchers.IO) {
                network.attendance.attendMeeting(
                    user = user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        ),
                    meetingId = meetingId,
                    tapTime = time,
                    verifiedBy = verifiedBy
                ).let {
                    when (it) {
                        is Result.Success -> {
                            usersDB.updateLoggedInUser(it.data, ::_setToken)
                            Result.Success(it.data)
                        }

                        is Result.Error -> when (val error = it.error) {
                            KtorError.AUTH -> Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in, then scan again.",
                                    401
                                )
                            )

                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> {
                                attendanceUploadCache.insert(
                                    meetingId,
                                    time,
                                    verifiedBy
                                ) //cache data to retry later when there is internet
                                Result.Error(Error("IO error: Attendance will be uploaded when you are connected to the internet."))
                            }

                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
                                .also {
                                    attendanceUploadCache.insert(
                                        meetingId,
                                        time,
                                        verifiedBy
                                    ) //cache data to retry later
                                }
                        }
                    }
                }
            }
        }

        override suspend fun delete(id: String): DataResult<Unit> {
            return withContext(Dispatchers.IO) {
                network.attendance.deleteMeeting(
                    user = user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        ),
                    id = id
                ).let {
                    when (it) {
                        is Result.Success -> {
                            meetingsDB.delete(id)
                            Result.Success(Unit)
                        }

                        is Result.Error -> when (val error = it.error) {
                            KtorError.AUTH -> Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            )

                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
                        }
                    }
                }
            }
        }

        private suspend fun uploadCachedAttendance(): List<DataResult<TokenUser>> {
            return withContext(Dispatchers.IO) {
                attendanceUploadCache.get().executeAsList().map { mtg ->
                    network.attendance.attendMeeting(
                        user = user()
                            ?: return@map Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            ),
                        meetingId = mtg.meeting_id,
                        tapTime = mtg.tap_time,
                        verifiedBy = mtg.verifiedBy
                    ).let {
                        when (it) {
                            is Result.Success -> it.also {
                                attendanceUploadCache.delete(it.data._id)
                            }

                            is Result.Error -> when (val error = it.error) {
                                KtorError.AUTH -> Result.Error(Error("Authentication Error", 401))
                                    .also { attendanceUploadCache.delete(mtg.meeting_id) }

                                is KtorError.CLIENT -> Result.Error(
                                    Error(
                                        "Error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )
                                    .also { attendanceUploadCache.delete(mtg.meeting_id) }

                                KtorError.IO -> Result.Error(Error("IO error: Attendance will be uploaded when you are connected to the internet."))
                                is KtorError.SERVER -> Result.Error(
                                    Error(
                                        "Server error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )
                            }
                        }
                    }
                }.also { users.sync() }
            }
        }

    }

    val seasons = object : SeasonsNamespace {
        override suspend fun sync(): DataResult<List<Season>> {
            return withContext(Dispatchers.IO) {
                network.getSeasons().let {
                    when (it) {
                        is Result.Success -> it.also {
                            seasonsDB.deleteAll()
                            seasonsDB.insert(it.data)
                        }

                        is Result.Error -> when (val error = it.error) {
                            is KtorError.CLIENT -> Result.Error(
                                Error(
                                    "Error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )

                            KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                            is KtorError.SERVER -> Result.Error(
                                Error(
                                    "Server error ${error.code}: ${error.message}",
                                    error.code
                                )
                            )
                        }
                    }
                }
            }
        }

        override fun getComps(year: Int): List<String> {
            return seasonsDB.getCompsFromSeason(year)
        }

        override fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String> {
            return seasonsDB.getCompsFromSeason(year).also {
                scope.launch {
                    sync().let {
                        if (it is Result.Success) onCompleteSync(seasonsDB.getCompsFromSeason(year))
                    }
                }
            }
        }

        override fun getAttendancePeriods(): List<String> {
            return seasonsDB.getAttendancePeriods()
        }

        override fun getAttendancePeriods(onCompleteSync: (List<String>) -> Unit): List<String> {
            return seasonsDB.getAttendancePeriods().also {
                scope.launch {
                    sync().let {
                        if (it is Result.Success) onCompleteSync(seasonsDB.getAttendancePeriods())
                    }
                }
            }
        }
    }

    val crescendo = object : CrescendoNamespace {

        override suspend fun sync(): CrescendoResult {
            return withContext(Dispatchers.IO) {
                val usr = user()
                val downloadRes =
                    if (usr == null) Result.Error(
                        Error(
                            "Authentication error: Your login session is invalid. Please log out and log back in.",
                            401
                        )
                    ) else network.crescendo.getCrescendos(
                        usr
                    ).let {
                        when (it) {
                            is Result.Success -> {
                                crescendoDB.deleteAll()
                                crescendoDB.insert(it.data)
                                it
                            }

                            is Result.Error -> when (val error = it.error) {
                                is KtorError.CLIENT -> Result.Error(
                                    Error(
                                        "Error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )

                                KtorError.IO -> Result.Error(Error("IO error. Please make sure you are connected to the internet."))
                                is KtorError.SERVER -> Result.Error(
                                    Error(
                                        "Server error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )

                                KtorError.AUTH -> Result.Error(
                                    Error(
                                        "Authentication error: Your login session is invalid. Please log out and log back in.",
                                        401
                                    )
                                )
                            }
                        }
                    }
                val uploadRes = crescendoUploadDB.getAll().map { (id, data) ->
                    network.crescendo.uploadCrescendo(
                        user()
                            ?: return@map Result.Error(
                                Error(
                                    "Authentication error: Your login session is invalid. Please log out and log back in.",
                                    401
                                )
                            ),
                        data
                    ).let {
                        when (it) {
                            is Result.Success -> {
                                crescendoUploadDB.delete(id)
                                it
                            }

                            is Result.Error -> when (val error = it.error) {
                                is KtorError.CLIENT -> Result.Error(
                                    Error(
                                        "Error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )

                                KtorError.IO -> Result.Error(Error("IO error. Your scouting data has been saved offline. Please connect to the internet and try again to upload your data."))
                                is KtorError.SERVER -> Result.Error(
                                    Error(
                                        "Server error ${error.code}: ${error.message}",
                                        error.code
                                    )
                                )

                                KtorError.AUTH -> Result.Error(
                                    Error(
                                        "Authentication error: Your login session is invalid. Please log out and log back in.",
                                        401
                                    )
                                )
                            }
                        }
                    }
                }
                CrescendoResult(upload = uploadRes, download = downloadRes)
            }
        }

        override fun getAll(): List<Crescendo> {
            return crescendoDB.getAll()
        }

        override fun getAll(onCompleteSync: (List<Crescendo>) -> Unit): List<Crescendo> {
            scope.launch {
                sync().let {
                    if (it.download is Result.Success) onCompleteSync(crescendoDB.getAll())
                }
            }
            return crescendoDB.getAll()
        }

        override fun clearDownloads() {
            crescendoDB.deleteAll()
        }


        override suspend fun upload(data: CrescendoSubmission): DataResult<Crescendo> {
            return withContext(Dispatchers.IO) {
                when (val res = network.crescendo.uploadCrescendo(
                    user()
                        ?: return@withContext Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        ),
                    data
                )) {
                    is Result.Success -> res.also { crescendoDB.insert(it.data) }
                    is Result.Error -> when (val error = res.error) {
                        is KtorError.CLIENT -> Result.Error(
                            Error(
                                "Error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        KtorError.IO -> {
                            crescendoUploadDB.insert(data)
                            Result.Error(Error("IO error. Your scouting data has been saved offline. Please connect to the internet and go to settings to upload your data."))
                        }

                        is KtorError.SERVER -> Result.Error(
                            Error(
                                "Server error ${error.code}: ${error.message}",
                                error.code
                            )
                        )

                        KtorError.AUTH -> Result.Error(
                            Error(
                                "Authentication error: Your login session is invalid. Please log out and log back in.",
                                401
                            )
                        )
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

    fun bgSync() {
        scope.launch {
            users.sync()
        }
        scope.launch {
            attendance.sync()
        }
        scope.launch {
            seasons.sync()
        }
        scope.launch {
            crescendo.sync()
        }
    }

    suspend fun sync(): SyncResult {
        val userres = scope.async {
            users.sync()
        }

        val attres = scope.async {
            attendance.sync()
        }

        val seasonres = scope.async {
            seasons.sync()
        }

        val cresres = scope.async {
            crescendo.sync()
        }

        return SyncResult(
            user = userres.await(),
            attendance = attres.await(),
            seasons = seasonres.await(),
            crescendo = cresres.await(),
        )
    }

    fun getNetworkClient() = network

    //interfaces are required for methods of namespace objects to be accessible,
    //   without making them implement an interface you just get an error that those methods
    //   don't exist on the type of the object
    interface UserNamspace {
        suspend fun login(username: String, password: String): DataResult<TokenUser>

        suspend fun login(
            username: String,
            password: String,
            onError: (Error) -> Unit
        ): TokenUser? {
            return this.login(username, password).unwrap(onError)
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
        ): DataResult<TokenUser>

        suspend fun register(
            username: String,
            password: String,
            email: String,
            firstName: String,
            lastName: String,
            subteam: Subteam,
            phone: String,
            grade: Int,
            onError: (Error) -> Unit
        ): TokenUser? {
            return this.register(
                username,
                password,
                email,
                firstName,
                lastName,
                subteam,
                phone,
                grade
            ).unwrap(onError)
        }

        fun logout()
        suspend fun refreshLoggedIn(): DataResult<TokenUser>
        suspend fun refreshLoggedIn(onError: (Error) -> Unit): TokenUser? {
            return this.refreshLoggedIn().unwrap(onError)
        }

        fun loadLoggedIn(): DataResult<TokenUser>
        fun loadLoggedIn(onError: (Error) -> Unit): TokenUser? {
            return this.loadLoggedIn().unwrap(onError)
        }

        fun loadAll(): List<User.WithoutToken>
        fun loadAll(onCompleteSync: (List<User.WithoutToken>) -> Unit): List<User.WithoutToken>
        fun clearAll()
        suspend fun sync(): SyncUserResult
        suspend fun deleteMe(): DataResult<Unit>
        suspend fun deleteMe(onError: (Error) -> Unit) {
            this.deleteMe().let {
                if (it is Result.Error) onError(it.error)
            }
        }
    }

    interface AttendanceNamespace {
        suspend fun sync(): AttendanceResult

        fun getAll(): List<Meeting>

        fun getAll(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getCurrent(time: Long): List<Meeting>

        fun getCurrent(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getOutdated(time: Long): List<Meeting>

        fun getOutdated(time: Long, onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        fun getArchived(): List<Meeting>

        fun getArchived(onCompleteSync: (List<Meeting>) -> Unit): List<Meeting>

        suspend fun archiveMeeting(id: String): DataResult<Unit>

        suspend fun archiveMeeting(id: String, onError: (Error) -> Unit) {
            this.archiveMeeting(id).let {
                if (it is Result.Error) onError(it.error)
            }
        }

        fun clear()

        suspend fun delete(id: String): DataResult<Unit>

        suspend fun delete(id: String, onError: (Error) -> Unit) {
            this.delete(id).let {
                if (it is Result.Error) onError(it.error)
            }
        }

        suspend fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
        ): DataResult<Meeting>

        suspend fun create(
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String,
            onError: (Error) -> Unit
        ): Meeting? {
            return this.create(startTime, endTime, type, description, value, attendancePeriod)
                .unwrap(onError)
        }

        suspend fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String,
        ): DataResult<TokenUser>

        suspend fun attend(
            meetingId: String,
            time: Long,
            verifiedBy: String,
            onError: (Error) -> Unit
        ): TokenUser? {
            return this.attend(meetingId, time, verifiedBy).unwrap(onError)
        }
    }

    interface SeasonsNamespace {
        suspend fun sync(): DataResult<List<Season>>
        suspend fun sync(onError: (Error) -> Unit): List<Season>? {
            return when (val res = this.sync()) {
                is Result.Success -> res.data
                is Result.Error -> {
                    onError(res.error)
                    null
                }

            }
        }

        fun getComps(year: Int): List<String>
        fun getComps(year: Int, onCompleteSync: (List<String>) -> Unit): List<String>
        fun getAttendancePeriods(): List<String>
        fun getAttendancePeriods(onCompleteSync: (List<String>) -> Unit): List<String>
    }

    interface CrescendoNamespace {
        suspend fun sync(): CrescendoResult
        fun getAll(): List<Crescendo>
        fun getAll(onCompleteSync: (List<Crescendo>) -> Unit): List<Crescendo>
        fun clearDownloads()
        suspend fun upload(data: CrescendoSubmission): DataResult<Crescendo>
        suspend fun upload(data: CrescendoSubmission, onError: (Error) -> Unit): Crescendo? {
            return this.upload(data).unwrap(onError)
        }
    }
}

fun DataResult<Crescendo>.unwrap(onError: (Error) -> Unit): Crescendo? {
    return when (this) {
        is Result.Success -> this.data
        is Result.Error -> {
            onError(this.error)
            null
        }
    }
}

data class Error(val message: String, val code: Int? = null)
typealias DataResult<T> = Result<T, Error>

data class SyncUserResult(
    val myUser: DataResult<TokenUser>,
    val userList: DataResult<List<User.WithoutToken>>
) {
    fun unwrapMyUser(onError: (Error) -> Unit): TokenUser? {
        return when (val res = myUser) {
            is Result.Success -> res.data
            is Result.Error -> {
                onError(res.error)
                null
            }
        }
    }

    fun unwrapUserList(onError: (Error) -> Unit): List<User.WithoutToken>? {
        return when (val res = userList) {
            is Result.Success -> res.data
            is Result.Error -> {
                onError(res.error)
                null
            }
        }
    }
}


//data class UploadDownloadResult<T, U>(val upload: T, val download: U)

interface UploadDownloadResult<T, U> {
    val upload: T
    val download: U
}

data class AttendanceResult(
    override val upload: List<DataResult<TokenUser>>,
    override val download: DataResult<List<Meeting>>
) : UploadDownloadResult<List<DataResult<TokenUser>>, DataResult<List<Meeting>>> {
    val uploadStatuses = upload.map { it is Result.Success }
    val uploadFailures = upload.filterIsInstance<Result.Error<Error>>().map { it.error }
    fun unwrapDownload(onError: (Error) -> Unit): List<Meeting>? {
        return when (val res = download) {
            is Result.Success -> res.data
            is Result.Error -> {
                onError(res.error)
                null
            }
        }
    }
}

data class CrescendoResult(
    override val upload: List<DataResult<Crescendo>>,
    override val download: DataResult<List<Crescendo>>
) : UploadDownloadResult<List<DataResult<Crescendo>>, DataResult<List<Crescendo>>> {
    val uploadStatuses = upload.map { it is Result.Success }
    val uploadFailures = upload.filterIsInstance<Result.Error<Error>>().map { it.error }
    fun unwrapDownload(onError: (Error) -> Unit): List<Crescendo>? {
        return when (val res = download) {
            is Result.Success -> res.data
            is Result.Error -> {
                onError(res.error)
                null
            }
        }
    }
}


data class UploadQueueLength(val attendance: Long, val crescendo: Long)

//data class AttendanceResult(
//    val uploadStatuses: List<Boolean>,
//    val uploadFailures: List<Error>,
//    val downloadData: List<Meeting>?,
//    val downloadError: Error?
//)

data class SyncResult(
    val user: SyncUserResult,
    val attendance: AttendanceResult,
    val seasons: DataResult<List<Season>>,
    val crescendo: CrescendoResult,
    val all: DataResult<Unit>,
    val allUnprotected: DataResult<Unit>
) {
    constructor(
        user: SyncUserResult,
        attendance: AttendanceResult,
        seasons: DataResult<List<Season>>,
        crescendo: CrescendoResult
    ) : this(
        user = user,
        attendance = attendance,
        seasons = seasons,
        crescendo = crescendo,
        all = with(
            listOf(
                user.myUser,
                user.userList,
                attendance.download,
                seasons,
                crescendo.download,
                *attendance.upload.toTypedArray(),
                *crescendo.upload.toTypedArray()
            )
        ) {
            if (all { it is Result.Success }) Result.Success(Unit)
            else Result.Error(Error(
                filterIsInstance<Result.Error<Error>>().toSet()
                    .joinToString(", ") { it.error.message }
            ))
        },
        allUnprotected = with(
            listOf(
                user.myUser,
                user.userList,
                attendance.download,
                seasons,
                crescendo.download,
                *attendance.upload.toTypedArray(),
                *crescendo.upload.toTypedArray()
            )
        ) {
            if (all {
                    it is Result.Success || (it is Result.Error && it.error.code in listOf(
                        401,
                        403
                    ))
                }) Result.Success(Unit)
            else Result.Error(Error(
                filterIsInstance<Result.Error<Error>>().filter {
                    it.error.code !in listOf(
                        403,
                        401
                    )
                }.toSet().joinToString(", ") { it.error.message }
            ))
        }
    )

    val allUnwrapped = all.unwrap()
    val allUnprotectedUnwrapped = allUnprotected.unwrap()

    fun unwrapSeasons(onError: (Error) -> Unit): List<Season>? {
        return when (val res = seasons) {
            is Result.Success -> res.data
            is Result.Error -> {
                onError(res.error)
                null
            }
        }
    }
}

fun DataResult<Unit>.unwrap(): OkOrMessage {
    return when (this) {
        is Result.Success -> OkOrMessage(true, null)
        is Result.Error -> OkOrMessage(false, this.error.message)
    }
}

fun DataResult<TokenUser>.unwrap(onError: (Error) -> Unit): TokenUser? {
    return when (this) {
        is Result.Success -> this.data
        is Result.Error -> {
            onError(this.error)
            null
        }
    }
}

fun DataResult<Meeting>.unwrap(onError: (Error) -> Unit): Meeting? {
    return when (this) {
        is Result.Success -> this.data
        is Result.Error -> {
            onError(this.error)
            null
        }
    }
}