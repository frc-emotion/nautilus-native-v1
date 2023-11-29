package org.team2658.apikt

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
import io.ktor.client.request.parameter
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
import org.team2658.apikt.models.ChargedUpScores
import org.team2658.apikt.models.ExamplePost
import org.team2658.apikt.models.UserModel
import org.team2658.apikt.models.safeDivide
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User

class EmotionClient {
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

    suspend fun login(username: String, password: String, errorCallback: (String) -> Unit = {}): User? {
        return try {
            val response = this.client.submitForm(url = ROUTES.LOGIN, formParameters = parameters {
                append("username", username)
                append("password", password)
            }).body<UserModel>()
           User.fromSerializable(response)
        }
        catch(e: ClientRequestException) {
            errorCallback(e.response.bodyAsText())
            null
        }
        catch(e: ServerResponseException) {
            errorCallback(e.response.bodyAsText())
            null
        }
        catch(e: Exception) {
            println(e)
            null
        }
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
        errorCallback: (String) -> Unit = {}
    ): User? {
        return try {
            val response = this.client.submitForm(url = ROUTES.REGISTER, formParameters = parameters {
                append("username", username)
                append("password", password)
                append("email", email)
                append("firstname", firstName)
                append("lastname", lastName)
                append("subteam", subteam.name.lowercase())
                append("phone", phone)
                append("grade", grade.toString())
            }).body<UserModel>()
            User.fromSerializable(response)
        }
        catch(e: ClientRequestException) {
            errorCallback(e.response.bodyAsText())
            null
        }
        catch(e: ServerResponseException) {
            errorCallback(e.response.bodyAsText())
            null
        }
        catch(e: Exception) { null }
    }

    suspend fun createMeeting(user: User?, startTime: Long, endTime: Long, type: String, description: String, value: Int, errorCallback: (String)-> Unit = {}): Meeting? {
        if(user != null && user.token?.isNotBlank() == true && user.permissions.verifyAllAttendance){
            println(user.token)
            return try {
                this.client.submitForm(url = ROUTES.CREATE_MEETING, formParameters = parameters {
                    append("createdBy", user._id)
                    append("startTime", startTime.toString())
                    append("endTime", endTime.toString())
                    append("type", type)
                    append("description", description)
                    append("value", value.toString())
                }){
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<Meeting>()
//                this.client.post {
//                    url(ROUTES.CREATE_MEETING)
//                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
//                    contentType(ContentType.Application.Json)
//                    setBody(Json {
//                        "startTime" to startTime
//                        "endTime" to endTime
//                        "type" to type
//                        "description" to description
//                        "value" to value
//                        "createdBy" to user.username
//                    })
//                }.body<Meeting>()
            }
            catch(e: ClientRequestException) {
                println(e.response.bodyAsText())
                errorCallback(e.response.bodyAsText())
                null
            }
            catch(e: ServerResponseException) {
                println(e.response.bodyAsText())
                errorCallback(e.response.bodyAsText())
                null
            }
            catch (e: Exception) {
                println("Issue with request")
                println(e.message)
                null }
        }
        println("issue with params")
        return null
    }

    suspend fun getMeetings(user: User?): List<Meeting>? {
        if(user != null && user.token?.isNotBlank() == true && user.permissions.verifyAllAttendance) {
            return try {
                this.client.get("${ROUTES.BASE}/attendance/getMeetings") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<List<Meeting>>()
            }
            catch(e: ClientRequestException) {
                println(e.response.bodyAsText())
                null
            }
            catch(e: ServerResponseException) {
                println(e.response.bodyAsText())
                null
            }
            catch(e: Exception) {
                println("Issue with request")
                println(e.message)
                null
            }
        }
        else return null
    }

    suspend fun attendMeeting(
        user: User?,
        meetingId: String,
        tapTime: Long,
        failureCallback: (String) -> Unit = {},
    ): User? {
        if(user != null && user.token?.isNotBlank() == true && user.accountType != AccountType.UNVERIFIED ) {
            return try {
                val response = this.client.submitForm(url = ROUTES.ATTEND_MEETING, formParameters = parameters {
                    append("userId", user._id)
                    append("meetingId", meetingId)
                    append("tapTime", tapTime.toString())
                }) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<UserModel>()
                User.fromSerializable(response)
            }
            catch(e: ClientRequestException){
                println(e.response.bodyAsText())
                failureCallback(e.response.bodyAsText())
                null
            }
            catch(e: ServerResponseException) {
                println(e.response.bodyAsText())
                failureCallback(e.response.bodyAsText())
                null
            }
            catch(e: Exception) {
                println("Issue with request")
                println(e.message)
                null
            }
        }
        return null
    }
     suspend fun getTest(): ExamplePost {
        return try {
            this.client.get("https://jsonplaceholder.typicode.com/posts/1").body()
        }
        catch(e: Exception) { ExamplePost(
            userId = -1,
            id = -1,
            title = "ERROR",
            body = "ERROR"
        ) }
    }

    suspend fun getUserById(id: String, user: User?): User? {
        return if(user != null && user.token?.isNotBlank() == true && user.isAdminOrLead) {
            try {
                val response: List<UserModel> = this.client.get("${ROUTES.BASE}/users?_id=$id")
                { header(HttpHeaders.Authorization, "Bearer ${user.token}") }
                    .body()
                println(response)
                User.fromSerializable(response.first())
            } catch (e: Exception) {
                println(e)
                null
            }
        }else null
    }

    suspend fun deleteMeeting(id: String, user: User?): Boolean { // true for success
        return if(user != null && user.token?.isNotBlank() == true && user.permissions.verifyAllAttendance) {
            try {
                this.client.delete("${ROUTES.BASE}/attendance/deleteMeeting/$id") {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }
                true
            } catch (e: Exception) {
                println(e)
                false
            }
        }else false
    }
    suspend fun getCompetitions(year: String): List<String> {
        return try {
            this.client.get("${ROUTES.BASE}/seasons/$year/competitions").body()
        } catch(e: Exception) {
            println(e)
            listOf()
        }
    }

    suspend fun getMe(user: User?): User? {
        if(user == null) return null
        return try {
            val response = this.client.get(ROUTES.ME) {
                header(HttpHeaders.Authorization, "Bearer ${user.token}")
            }.body<UserModel>()
            User.fromSerializable(response)
        } catch(e: Exception) {
            println(e)
            null
        }
    }

    suspend fun submitChargedUp(data: ChargedUpRequestParams, user: User?):String? {
        return if(user?.permissions?.standScouting == true && user.token?.isNotBlank() == true) {
            try {
                println(data)
                this.client.post(ROUTES.CHARGEDUP) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                    setBody(data)
                }.bodyAsText()
            }catch(e: Exception) {
                println(e.message)
                null
            }
        } else null
    }
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