package org.team2658.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team2658.nautilus.Result
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.scouting.scoutingdata.Crescendo
import org.team2658.nautilus.scouting.scoutingdata.CrescendoRequestBody
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.FullUser
import org.team2658.nautilus.userauth.PartialUser
import org.team2658.nautilus.userauth.Subteam
import org.team2658.nautilus.userauth.TokenUser
import org.team2658.nautilus.userauth.User
import org.team2658.nautilus.userauth.isAdmin
import org.team2658.network.models.Season

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
    
    fun close () {
        this.client.close()
    }
    
    private val routes = Routes(base)
    suspend fun getAppManifest(): Result<String, KtorError> {
        return withContext(Dispatchers.IO) {
            try {
                val res = client.get("${routes.base}/swagger/json").bodyAsText()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }
    }

    val users = object: UsersNamespace {
        override suspend fun login(username: String, password: String): Result<TokenUser, KtorError.NoAuthRequired> {
            return try {
                val response = client.submitForm(url = routes.login, formParameters = parameters {
                    append("username", username)
                    append("password", password)
                }).body<TokenUser>()
                Result.Success(response)
            }
            catch(e: ClientRequestException) {
                //TODO make different responses depending on if developer mode enabled
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                println(e)
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
                val response = client.submitForm(url = routes.register, formParameters = parameters {
                    append("username", username)
                    append("password", password)
                    append("email", email)
                    append("firstname", firstName)
                    append("lastname", lastName)
                    append("subteam", subteam.name.lowercase())
                    append("phone", phone)
                    append("grade", grade.toString())
                }).body<TokenUser>()
                Result.Success(response)
            }
            catch(e: ClientRequestException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
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
        override suspend fun getUserById(id: String, user: TokenUser): Result<User.WithoutToken, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val responseText = client.get("${routes.users}/$id")
                { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                    .bodyAsText()
                val response = if(isAdmin(user)) Json.decodeFromString<FullUser>(responseText)
                else Json.decodeFromString<PartialUser>(responseText)
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
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val responseText  = client.get(routes.users)
                { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                    .bodyAsText()

                val response = if(isAdmin(user)) Json.decodeFromString<List<FullUser>>(responseText)
                    else Json.decodeFromString<List<PartialUser>>(responseText)

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
            if(token == null) return Result.Error(KtorError.AUTH)
            return try {
                val response = client.get(routes.me) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }.body<TokenUser>()
                Result.Success(response)
            } catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun deleteMe(user: TokenUser): Result<Unit, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                client.delete(routes.me) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(Json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch(e: ClientRequestException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch(e: ServerResponseException) {
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }
    }

    val attendance = object: AttendanceNamespace {
        override suspend fun createMeeting(
            user: TokenUser,
            startTime: Long,
            endTime: Long,
            type: String,
            description: String,
            value: Int,
            attendancePeriod: String
        ): Result<Meeting, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.submitForm(url = routes.meetings, formParameters = parameters {
                    append("startTime", startTime.toString())
                    append("endTime", endTime.toString())
                    append("type", type)
                    append("description", description)
                    append("value", value.toString())
                    append("attendancePeriod", attendancePeriod)
                }){
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<Meeting>()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch (e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun getMeetings(user: TokenUser): Result<List<Meeting>, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            val route = "${routes.meetings}/${if(isAdmin(user)) "all" else "current"}" //routes.meetings/all for admin, routes.meetings/current for lead
            return try {
                val res = client.get(route) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Meeting>>()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun attendMeeting(
            user: TokenUser,
            meetingId: String,
            tapTime: Long,
            verifiedBy: String, ): Result<TokenUser, KtorError> {
            if (user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val response = client.submitForm(
                    url = "${routes.meetings}/attend/$meetingId",
                    formParameters = parameters {
                        append("tapTime", tapTime.toString())
                        append("verifiedBy", verifiedBy)
                    }) {
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
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                client.delete("${routes.meetings}/$id") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(Json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun archiveMeeting(id: String, user: TokenUser): Result<Unit, KtorError> {
            if(user.isInvalid() ) return Result.Error(KtorError.AUTH)
            return try {
                client.put("${routes.meetings}/archive/$id") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(Json.encodeToString(Unit))
                }
                Result.Success(Unit)
            } catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            } catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

    }

    val crescendo = object: CrescendoNamespace {
        override suspend fun getCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.get("${routes.base}/crescendo") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Crescendo>>()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun uploadCrescendo(
            user: TokenUser,
            data: CrescendoRequestBody
        ): Result<Crescendo, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.post("${routes.base}/crescendo") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(data))
                }.body<Crescendo>()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun getMyCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError> {
            if(user.isInvalid()) return Result.Error(KtorError.AUTH)
            return try {
                val res = client.get("${routes.base}/crescendo/mine") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Crescendo>>()
                Result.Success(res)
            }
            catch(e: ClientRequestException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
            }
            catch(e: ServerResponseException) {
                e.printStackTrace()
                val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
                Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

    }

    suspend fun getSeasons(): Result<List<Season>, KtorError.NoAuthRequired> {
        return try {
            Result.Success(this.client.get("${routes.base}/seasons").body())
        } catch(e: ClientRequestException) {
            e.printStackTrace()
            val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
            Result.Error(KtorError.CLIENT(serverResponse, e.response.status.value))
        } catch(e: ServerResponseException) {
            e.printStackTrace()
            val serverResponse = ServerMessage.readMessage(e.response.bodyAsText()) ?: e.message
            Result.Error(KtorError.SERVER(serverResponse, e.response.status.value))
        } catch(e: Exception) {
            println(e)
            Result.Error(KtorError.IO)
        }
    }

    interface UsersNamespace {
        suspend fun login(username: String, password: String): Result<TokenUser, KtorError.NoAuthRequired>
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
        suspend fun createMeeting(user: TokenUser, startTime: Long, endTime: Long, type: String, description: String, value: Int, attendancePeriod: String): Result<Meeting, KtorError>
        suspend fun getMeetings(user: TokenUser): Result<List<Meeting>, KtorError>
        suspend fun attendMeeting(user: TokenUser, meetingId: String, tapTime: Long, verifiedBy: String ): Result<TokenUser, KtorError>
        suspend fun deleteMeeting(id: String, user: TokenUser): Result<Unit, KtorError>
        suspend fun archiveMeeting(id: String, user: TokenUser): Result<Unit, KtorError>
    }

    interface CrescendoNamespace {
        suspend fun getCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError>
        suspend fun uploadCrescendo(user: TokenUser, data: CrescendoRequestBody): Result<Crescendo, KtorError>
        suspend fun getMyCrescendos(user: TokenUser): Result<List<Crescendo>, KtorError>
    }

}

sealed interface KtorError {
    sealed interface NoAuthRequired: KtorError
    data class CLIENT(val message: String, val code: Int): KtorError, NoAuthRequired
    data class SERVER(val message: String, val code: Int): KtorError, NoAuthRequired
    data object IO: KtorError, NoAuthRequired
    data object AUTH: KtorError
}

fun TokenUser.isInvalid() = token.isBlank() || accountType == AccountType.UNVERIFIED