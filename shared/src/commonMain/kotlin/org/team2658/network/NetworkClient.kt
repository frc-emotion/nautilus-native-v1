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
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.team2658.nautilus.Result
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.Subteam
import org.team2658.nautilus.userauth.User
import org.team2658.network.models.ChargedUpScores
import org.team2658.network.models.Season
import org.team2658.network.models.UserModel
import org.team2658.network.models.safeDivide

class NetworkClient {
    private val client = HttpClient() {
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

    val users = object: UsersNamespace {
        override suspend fun login(username: String, password: String, errorCallback: (String) -> Unit): Result<User, KtorError> {
            return try {
                val response = client.submitForm(url = ROUTES.LOGIN, formParameters = parameters {
                    append("username", username)
                    append("password", password)
                }).body<UserModel>()
                Result.Success(User.fromSerializable(response))
            }
            catch(e: ClientRequestException) {
                errorCallback(e.message)
                Result.Error(KtorError.CLIENT(e.message))
            }
            catch(e: ServerResponseException) {
                errorCallback(e.message)
                Result.Error(KtorError.SERVER(e.message))
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
            grade: Int,
            errorCallback: (String) -> Unit
        ): Result<User, KtorError> {
            return try {
                val response = client.submitForm(url = ROUTES.REGISTER, formParameters = parameters {
                    append("username", username)
                    append("password", password)
                    append("email", email)
                    append("firstname", firstName)
                    append("lastname", lastName)
                    append("subteam", subteam.name.lowercase())
                    append("phone", phone)
                    append("grade", grade.toString())
                }).body<UserModel>()
                Result.Success(User.fromSerializable(response))
            }
            catch(e: ClientRequestException) {
                errorCallback(e.message)
                Result.Error(KtorError.CLIENT(e.message))
            }
            catch(e: ServerResponseException) {
                errorCallback(e.message)
                Result.Error(KtorError.SERVER(e.message))
            }
            catch(e: Exception) {
                e.printStackTrace()
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun getUserById(id: String, user: User?): Result<User, KtorError> {
            return if(user != null && user.token?.isNotBlank() == true && user.isAdminOrLead) {
                try {
                    val response: List<UserModel> = client.get("${ROUTES.BASE}/users?_id=$id")
                    { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                        .body()
                    println(response)
                    Result.Success(User.fromSerializable(response.first()))
                } catch (e: ClientRequestException) {
                    println(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                } catch (e: ServerResponseException) {
                    println(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                } catch (e: Exception) {
                    println(e)
                    Result.Error(KtorError.IO)
                }
            } else Result.Error(KtorError.AUTH)
        }

        override suspend fun getUsers(user: User?): Result<List<User>, KtorError> {
            return if(user != null && user.token?.isNotBlank() == true && user.isAdminOrLead) {
                try {
                    val response: List<UserModel> = client.get("${ROUTES.BASE}/users")
                    { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                        .body()
                    println(response)
                    Result.Success(response.map { User.fromSerializable(it) })
                } catch (e: ClientRequestException) {
                    println(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                } catch (e: ServerResponseException) {
                    println(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                } catch (e: Exception) {
                    println(e)
                    Result.Error(KtorError.IO)
                }
            } else Result.Error(KtorError.AUTH)
        }

        override suspend fun getMe(token: String?): Result<User, KtorError> {
            if(token == null) return Result.Error(KtorError.AUTH)
            return try {
                val response = client.get(ROUTES.ME) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }.body<UserModel>()
                Result.Success(User.fromSerializable(response))
            } catch(e: ClientRequestException) {
                println(e.message)
                Result.Error(KtorError.CLIENT(e.message))
            } catch(e: ServerResponseException) {
                println(e.message)
                Result.Error(KtorError.SERVER(e.message))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }

        override suspend fun deleteMe(user: User?, callback: (Boolean, String) -> Unit): Result<Unit, KtorError> {
            if(user == null) return Result.Error(KtorError.AUTH)
            return try {
                client.delete(ROUTES.ME) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }
                callback(true, "Successfully deleted account")
                Result.Success(Unit)
            } catch(e: ClientRequestException) {
                callback(false, e.message)
                Result.Error(KtorError.CLIENT(e.message))
            } catch(e: ServerResponseException) {
                callback(false, e.message)
                Result.Error(KtorError.SERVER(e.message))
            } catch(e: Exception) {
                println(e)
                Result.Error(KtorError.IO)
            }
        }
    }

    val attendance = object: AttendanceNamespace {
        override suspend fun createMeeting(user: User?, startTime: Long, endTime: Long, type: String, description: String, value: Int, attendancePeriod: String, errorCallback: (String)-> Unit): Result<Meeting, KtorError> {
            if(user != null && user.token?.isNotBlank() == true && user.permissions.verifyAllAttendance){
                println(user.token)
                return try {
                    val res = client.submitForm(url = ROUTES.CREATE_MEETING, formParameters = parameters {
                        append("createdBy", user._id)
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
                    println(e.message)
                    errorCallback(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                }
                catch(e: ServerResponseException) {
                    println(e.message)
                    errorCallback(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                }
                catch (e: Exception) {
                    println(e.message)
                    Result.Error(KtorError.IO)
                }
            }
            return Result.Error(KtorError.AUTH)
        }

        override suspend fun getMeetings(user: User?): Result<List<Meeting>, KtorError> {
            val route = when(user?.accountType?.value) {
                in AccountType.ADMIN.value..Int.MAX_VALUE -> "${ROUTES.BASE}/attendance/getAll"
                else -> "${ROUTES.BASE}/attendance/getMeetings"
            }
            if(user != null && user.token?.isNotBlank() == true && user.permissions.verifyAllAttendance && user.accountType.value >= AccountType.LEAD.value) {
                return try {
                    val res = client.get(route) {
                        header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    }.body<List<Meeting>>()
                    Result.Success(res)
                }
                catch(e: ClientRequestException) {
                    println(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                }
                catch(e: ServerResponseException) {
                    println(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                }
                catch(e: Exception) {
                    println("Issue with request")
                    println(e.message)
                    Result.Error(KtorError.IO)
                }
            }
            else return Result.Error(KtorError.AUTH)
        }

        override suspend fun attendMeeting(user: User?, meetingId: String, tapTime: Long, failureCallback: (String) -> Unit, ): Result<User, KtorError> {
            return if(user != null && user.token?.isNotBlank() == true ) {
                return try {
                    val response = client.submitForm(url = ROUTES.ATTEND_MEETING, formParameters = parameters {
                        append("meetingId", meetingId)
                        append("tapTime", tapTime.toString())
                    }) {
                        header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    }.body<UserModel>()
                    Result.Success(User.fromSerializable(response))
                }
                catch(e: ClientRequestException){
                    println(e.message)
                    failureCallback(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                }
                catch(e: ServerResponseException) {
                    println(e.message)
                    failureCallback(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                }
                catch(e: Exception) {
                    println(e.message)
                    failureCallback("An error occurred trying to reach the server: ${e.message}\n Your attendance will be updated when you connect to the internet")
                    Result.Error(KtorError.IO)
                }
            } else {
                failureCallback("Error: User not logged in")
                Result.Error(KtorError.AUTH)
            }
        }

        override suspend fun deleteMeeting(id: String, user: User?): Result<Unit, KtorError> { // true for success
            return if(user != null && user.token?.isNotBlank() == true && user.isAdminOrLead) {
                try {
                    client.delete("${ROUTES.BASE}/attendance/deleteMeeting/$id") {
                        header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    }
                    Result.Success(Unit)
                } catch(e: ClientRequestException) {
                    println(e.message)
                    Result.Error(KtorError.CLIENT(e.message))
                } catch(e: ServerResponseException) {
                    println(e.message)
                    Result.Error(KtorError.SERVER(e.message))
                } catch(e: Exception) {
                    println(e)
                    Result.Error(KtorError.IO)
                }
            } else Result.Error(KtorError.AUTH)
        }

    }

    suspend fun getSeasons(): Result<List<Season>, KtorError> {
        return try {
            Result.Success(this.client.get("${ROUTES.BASE}/seasons").body())
        } catch(e: ClientRequestException) {
            println(e.message)
            Result.Error(KtorError.CLIENT(e.message))
        } catch(e: ServerResponseException) {
            println(e.message)
            Result.Error(KtorError.SERVER(e.message))
        } catch(e: Exception) {
            println(e)
            Result.Error(KtorError.IO)
        }
    }

    suspend fun submitChargedUp(data: ChargedUpRequestParams, user: User?): Result<String, KtorError> {
        return if(user?.permissions?.standScouting == true && user.token?.isNotBlank() == true) {
            try {
                println(data)
                val response = this.client.post(ROUTES.CHARGEDUP) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(data)
                }.bodyAsText()
                Result.Success(response)
            } catch(e: ClientRequestException) {
                println(e.message)
                Result.Error(KtorError.CLIENT(e.message))
            } catch(e: ServerResponseException) {
                println(e.message)
                Result.Error(KtorError.SERVER(e.message))
            } catch(e: Exception) {
                println(e.message)
                Result.Error(KtorError.IO)
            }
        } else Result.Error(KtorError.AUTH)
    }

    interface UsersNamespace {
        suspend fun login(username: String, password: String, errorCallback: (String) -> Unit = {}): Result<User, KtorError>
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
        ): Result<User, KtorError>
        suspend fun getUserById(id: String, user: User?): Result<User, KtorError>
        suspend fun getUsers(user: User?): Result<List<User>, KtorError>
        suspend fun getMe(token: String?): Result<User, KtorError>
        suspend fun deleteMe(user: User?, callback: (Boolean, String) -> Unit): Result<Unit, KtorError>
    }
    interface AttendanceNamespace {
        suspend fun createMeeting(user: User?, startTime: Long, endTime: Long, type: String, description: String, value: Int, attendancePeriod: String, errorCallback: (String)-> Unit = {}): Result<Meeting, KtorError>
        suspend fun getMeetings(user: User?): Result<List<Meeting>, KtorError>
        suspend fun attendMeeting( user: User?, meetingId: String, tapTime: Long, failureCallback: (String) -> Unit = {}, ): Result<User, KtorError>
        suspend fun deleteMeeting(id: String, user: User?): Result<Unit, KtorError>
    }

}

sealed interface KtorError {
    data class CLIENT(val message: String): KtorError
    data class SERVER(val message: String): KtorError
    data object IO: KtorError
    data object AUTH: KtorError
}

@Serializable
data class ChargedUpRequestParams(
    val competition: String,
    val matchNumber: Int,
    val teamNumber: Int,
//    val teamName: String,
    val RPEarned: List<Boolean>,
    val totalRP: Int,
    val autoPeriod: ChargedUpScores,
    val teleopPeriod: ChargedUpScores,
    val coneRate: Double?= safeDivide((teleopPeriod.totalCones + autoPeriod.totalCones), (teleopPeriod.totalScore + autoPeriod.totalScore)),
    val cubeRate: Double?= safeDivide((teleopPeriod.totalCubes + autoPeriod.totalCubes), (teleopPeriod.totalScore + autoPeriod.totalScore)),
    val linkScore: Int,
    val autoDock: Boolean,
    val autoEngage: Boolean,
    val teleopDock: Boolean,
    val teleopEngage: Boolean,
    val parked: Boolean,
    val isDefensive: Boolean,
    val didBreak: Boolean,
    val penaltyCount: Int,
    val score: Int,
    val won: Boolean,
    val tied: Boolean,
    val comments: String,
    val teamName: String = "",
){
//    val coneRate = safeDivide((this.teleopPeriod.totalCones + this.autoPeriod.totalCones), (this.teleopPeriod.totalScore + this.autoPeriod.totalScore))
//    val cubeRate = safeDivide((this.teleopPeriod.totalCubes + this.autoPeriod.totalCubes), (this.teleopPeriod.totalScore + this.autoPeriod.totalScore))
}