package org.nautilusapp.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.attendance.Meeting
import org.nautilusapp.nautilus.roles.UserRole
import org.nautilusapp.nautilus.scouting.pit.Inpit
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoSubmission
import org.nautilusapp.nautilus.userauth.FullUser
import org.nautilusapp.nautilus.userauth.PartialUser
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.TokenUser
import org.nautilusapp.nautilus.userauth.User
import org.nautilusapp.nautilus.userauth.isAdmin
import org.nautilusapp.network.models.Season
import org.nautilusapp.openapi.SwaggerManifest

class NetworkClient(base: String) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        expectSuccess = true
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    var rootURL = base
        private set

    fun setRootURL(url: String) {
        rootURL = url
        println(rootURL)
    }

    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    fun close() {
        this.client.close()
    }


    private enum class Routes(val relative: String) {
        SWAGGER("/swagger/json"),
        LOGIN("/users/login"),
        USERS("/users"),
        USERS_USER("/users/{user}"),
        REGISTER("/users/register"),
        MEETINGS("/attendance/meetings"),
        MEETINGS_CURRENT("/attendance/meetings/current"),
        MEETINGS_ALL("/attendance/meetings/all"),
        ATTEND("/attendance/meetings/attend/{meetingId}"),
        ME("/users/me"),
        MEETINGS_ARCHIVE("/attendance/meetings/archive/{meetingId}"),
        CRESCENDO("/crescendo"),
        CRESCENDO_MINE("/crescendo/mine"),
        CRESCENDO_SUBMISSION("/crescendo/{id}"),
        SEASONS("/seasons"),
        MEETINGS_MEETING("/attendance/meetings/{meetingId}"),
        USERS_VERIFY("/users/{user}/verify"),
        ROLES("/roles"),
        USER_ROLES_REVOKE("/users/{user}/roles/revoke"),
        USER_ROLES_ASSIGN("/users/{user}/roles/assign"),
        INPIT("/inpit"),
        INPIT_TEAM("/inpit/{team}")
    }

    private val Routes.path: String
        get() = rootURL + this.relative

    val relativePaths = Routes.entries.map { it.relative }


    suspend fun getAppManifest(): Result<SwaggerManifest, KtorError> {
        return withContext(Dispatchers.IO) {
            try {
                val res = client.get(Routes.SWAGGER.path).body<SwaggerManifest>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }
    }

    val users = object : UsersNamespace {
        override suspend fun login(
            username: String,
            password: String
        ): Result<TokenUser, KtorError.NoAuthRequired> {
            return try {
                val response = client.post(Routes.LOGIN.path) {
                    setBody(Login(username, password))
                }.body<TokenUser>()
                Result.Success(response)
            } catch (e: ClientRequestException) {
                //TODO make different responses depending on if developer mode enabled
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun verify(
            myUser: TokenUser?,
            verifying: String
        ): Result<FullUser, KtorError> {
            if (myUser == null) return Result.Error(KtorError.AUTH)
            return try {
                val response =
                    client.put(Routes.USERS_VERIFY.path.replace("{user}", verifying)) {
                        header(HttpHeaders.Authorization, "Bearer ${myUser.token}")
                    }.body<FullUser>()
                Result.Success(response)
            } catch (e: ClientRequestException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
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
        ): Result<TokenUser, KtorError.NoAuthRequired> {
            return try {
                val response = client.post(Routes.REGISTER.path) {
                    setBody(
                        Register(
                            username = username,
                            password = password,
                            email = email,
                            firstname = firstName,
                            lastname = lastName,
                            subteam = subteam.name.lowercase(),
                            phone = phone,
                            grade = grade
                        )
                    )
                }
                    .body<TokenUser>()
                Result.Success(response)
            } catch (e: ClientRequestException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        /**
         * Get a user by their username, email, or UUID
         *
         * @return If the authenticating user is an admin, [FullUser], else if they are base [PartialUser].
         * If the authenticating user is null or unverified, or has an invalid token, returns a [KtorError.AUTH].
         * @param id the username, email, or UUID of the user to get
         * @param user the user to authenticate the request with
         * @see [getUsers]
         */
        override suspend fun getUserById(
            id: String,
            user: TokenUser
        ): Result<User.WithoutToken, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            val route = Routes.USERS_USER.path.replace("{user}", id)
            return try {
                val responseText = client.get(route)
                { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                    .bodyAsText()
                val response = if (user.isAdmin) json.decodeFromString<FullUser>(responseText)
                else json.decodeFromString<PartialUser>(responseText)
                Result.Success(response)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        /**
         * Get the list of users on the team
         * @param user the user to authenticate the request with
         * @return If the authenticating user is an admin, List<[FullUser]>, else if they are base List<[PartialUser]>.
         * If the authenticating user is null or unverified, or has an invalid token, returns a [KtorError.AUTH].
         * @see [getUserById]
         */
        override suspend fun getUsers(user: TokenUser): Result<List<User.WithoutToken>, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val responseText = client.get(Routes.USERS.path)
                { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                    .bodyAsText()

                val response =
                    if (user.isAdmin) json.decodeFromString<List<FullUser>>(responseText)
                    else json.decodeFromString<List<PartialUser>>(responseText)

                Result.Success(response)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        /**
         * Get an updated version of the current user's data.
         * @param token the user's JWT
         * @return [TokenUser] if the token is valid, [KtorError.AUTH] if the token is invalid.
         */
        override suspend fun getMe(token: String?): Result<TokenUser, KtorError> {
            if (token == null) return Result.Error(KtorError.AUTH)
            return try {
                val response = client.get(Routes.ME.path) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }.body<TokenUser>()
                Result.Success(response)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun deleteMe(user: TokenUser): Result<Unit, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                client.delete(Routes.ME.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch (e: ClientRequestException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }
    }

    val attendance = object : AttendanceNamespace {
        override suspend fun createMeeting(
            user: TokenUser,
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String
        ): Result<Meeting, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.post(Routes.MEETINGS.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(
                        CreateMeeting(
                            startTime,
                            endTime,
                            type,
                            description,
                            value,
                            attendancePeriod
                        )
                    )
                }.body<Meeting>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun getMeetings(user: TokenUser): Result<List<Meeting>, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            val route =
                if (user.isAdmin) Routes.MEETINGS_ALL.path else Routes.MEETINGS_CURRENT.path //routes.meetings/all for admin, routes.meetings/current for lead
            return try {
                val res = client.get(route) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Meeting>>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun attendMeeting(
            user: TokenUser,
            meetingId: String,
            tapTime: Long,
            verifiedBy: String,
        ): Result<TokenUser, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val response = client.post(
                    Routes.ATTEND.path.replace("{meetingId}", meetingId)
                ) {
                    setBody(Attend(tapTime, verifiedBy))
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<TokenUser>()
                Result.Success(response)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun deleteMeeting(id: String, user: TokenUser): Result<Unit, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            val route = Routes.MEETINGS_MEETING.path.replace("{meetingId}", id)
            return try {
                client.delete(route) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun archiveMeeting(id: String, user: TokenUser): Result<Unit, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            val route = Routes.MEETINGS_ARCHIVE.path.replace("{meetingId}", id)
            return try {
                client.put(route) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

    }

    val crescendo = object : CrescendoNamespace {
        override suspend fun getCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.get(Routes.CRESCENDO.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Crescendo>>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun uploadCrescendo(
            user: TokenUser,
            data: CrescendoSubmission
        ): Result<Crescendo, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.post(Routes.CRESCENDO.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    contentType(ContentType.Application.Json)
                    setBody(json.encodeToString(data))
                }.body<Crescendo>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun getMyCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.get(Routes.CRESCENDO_MINE.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Crescendo>>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun deleteCrescendo(user: TokenUser, id: String): Result<Unit, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                client.delete(Routes.CRESCENDO_SUBMISSION.path.replace("{id}", id)) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

    }

    val roles = object : RolesNamespace {
        override suspend fun getRoles(user: TokenUser?): Result<List<UserRole>, KtorError> {
            if (user == null) return Result.Error(KtorError.AUTH)
            return try {
                val result = client.get(Routes.ROLES.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<UserRole>>()
                Result.Success(result)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun assignRoles(
            myUser: TokenUser?,
            assigningTo: String,
            roleIds: List<String>
        ): Result<FullUser, KtorError> {
            if (myUser == null) return Result.Error(KtorError.AUTH)
            return try {
                val result =
                    client.put(
                        Routes.USER_ROLES_ASSIGN.path.replace(
                            "{user}",
                            assigningTo
                        )
                    ) {
                        header(HttpHeaders.Authorization, "Bearer ${myUser.token}")
                        setBody(roleIds)
                    }.body<FullUser>()
                Result.Success(result)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun revokeRoles(
            myUser: TokenUser?,
            revokingFrom: String,
            roleIds: List<String>
        ): Result<FullUser, KtorError> {
            if (myUser == null) return Result.Error(KtorError.AUTH)
            return try {
                val result =
                    client.put(
                        Routes.USER_ROLES_REVOKE.path.replace(
                            "{user}",
                            revokingFrom
                        )
                    ) {
                        header(HttpHeaders.Authorization, "Bearer ${myUser.token}")
                        setBody(roleIds)
                    }.body<FullUser>()
                Result.Success(result)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

    }

    suspend fun getSeasons(): Result<List<Season>, KtorError.NoAuthRequired> {
        return try {
            Result.Success(this.client.get(Routes.SEASONS.path).body())
        } catch (e: ClientRequestException) {
            e.printStackTrace()
            val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
            Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
        } catch (e: ServerResponseException) {
            e.printStackTrace()
            val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
            Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
        } catch (e: Exception) {
            println(e)
            Result.Error(KtorError.IO)
        }
    }

    val inpit = object : InpitNamespace {
        override suspend fun get(user: TokenUser): Result<List<Inpit>, KtorError> {
            return try {
                val res = client.get(Routes.INPIT.path) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Inpit>>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun post(
            user: TokenUser,
            data: Map<String, String>,
            unset: List<String>,
            teamNumber: Int
        ): Result<Inpit, KtorError> {
            return try {
                val res =
                    client.post(Routes.INPIT.path) {
                        header(HttpHeaders.Authorization, "Bearer ${user.token}")
                        setBody(mapOf("data" to data, "unset" to unset, "teamNumber" to teamNumber))
                    }.body<Inpit>()
                Result.Success(res)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun delete(user: TokenUser, team: Int): Result<Unit, KtorError> {
            return try {
                client.delete(Routes.INPIT_TEAM.path.replace("{team}", team.toString())) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }
                Result.Success(Unit)
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch (e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

    }

    interface UsersNamespace {
        suspend fun login(
            username: String,
            password: String
        ): Result<TokenUser, KtorError.NoAuthRequired>

        suspend fun verify(
            myUser: TokenUser?,
            verifying: String
        ): Result<FullUser, KtorError>

        suspend fun register(
            username: String,
            password: String,
            email: String,
            firstName: String,
            lastName: String,
            subteam: Subteam,
            phone: String,
            grade: Int,
        ): Result<TokenUser, KtorError.NoAuthRequired>

        suspend fun getUserById(id: String, user: TokenUser): Result<User.WithoutToken, KtorError>
        suspend fun getUsers(user: TokenUser): Result<List<User.WithoutToken>, KtorError>
        suspend fun getMe(token: String?): Result<TokenUser, KtorError>
        suspend fun deleteMe(user: TokenUser): Result<Unit, KtorError>
    }

    interface AttendanceNamespace {
        suspend fun createMeeting(
            user: TokenUser,
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String
        ): Result<Meeting, KtorError>

        suspend fun getMeetings(user: TokenUser): Result<List<Meeting>, KtorError>
        suspend fun attendMeeting(
            user: TokenUser,
            meetingId: String,
            tapTime: Long,
            verifiedBy: String
        ): Result<TokenUser, KtorError>

        suspend fun deleteMeeting(id: String, user: TokenUser): Result<Unit, KtorError>
        suspend fun archiveMeeting(id: String, user: TokenUser): Result<Unit, KtorError>
    }

    interface CrescendoNamespace {
        suspend fun getCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError>
        suspend fun uploadCrescendo(
            user: TokenUser,
            data: CrescendoSubmission
        ): Result<Crescendo, KtorError>

        suspend fun getMyCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError>

        suspend fun deleteCrescendo(
            user: TokenUser,
            id: String
        ): Result<Unit, KtorError>
    }

    interface RolesNamespace {
        suspend fun getRoles(user: TokenUser?): Result<List<UserRole>, KtorError>
        suspend fun assignRoles(
            myUser: TokenUser?,
            assigningTo: String,
            roleIds: List<String>
        ): Result<FullUser, KtorError>

        suspend fun revokeRoles(
            myUser: TokenUser?,
            revokingFrom: String,
            roleIds: List<String>
        ): Result<FullUser, KtorError>
    }

    interface InpitNamespace {
        suspend fun get(user: TokenUser): Result<List<Inpit>, KtorError>

        suspend fun post(
            user: TokenUser,
            data: Map<String, String>,
            unset: List<String>,
            teamNumber: Int
        ): Result<Inpit, KtorError>

        suspend fun delete(user: TokenUser, team: Int): Result<Unit, KtorError>
    }

}

sealed interface KtorError {
    sealed interface NoAuthRequired : KtorError
    data class CLIENT(val message: String, val code: Int) : KtorError, NoAuthRequired
    data class SERVER(val message: String, val code: Int) : KtorError, NoAuthRequired
    data object IO : KtorError, NoAuthRequired
    data object AUTH : KtorError
}

fun TokenUser.isInvalid() = token.isBlank()

@Serializable
data class Register(
    val username: String,
    val password: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val subteam: String,
    val phone: String,
    val grade: Int
)

@Serializable
data class Login(
    val username: String,
    val password: String
)

@Serializable
data class CreateMeeting(
    val startTime: Long,
    val endTime: Long,
    val type: String,
    val description: String,
    val value: Int,
    val attendancePeriod: String
)

@Serializable
data class Attend(
    val tapTime: Long,
    val verifiedBy: String
)