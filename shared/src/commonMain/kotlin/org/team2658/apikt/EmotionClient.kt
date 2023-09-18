package org.team2658.apikt

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.team2658.apikt.responses.ExamplePostResponse
import org.team2658.apikt.responses.UserResponse
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.userauth.AccountType
import org.team2658.emotion.userauth.Role
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import org.team2658.emotion.userauth.UserPermissions

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

    suspend fun login(username: String, password: String): User? {
        return try {
            val response = this.client.submitForm(url = ROUTES.LOGIN, formParameters = parameters {
                append("username", username)
                append("password", password)
            }).body<UserResponse>()
           User.fromSerializable(response)
        }catch(e: Exception) { null }
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
            }).body<UserResponse>()
            User.fromSerializable(response)
        }catch(e: Exception) { null }
    }

    suspend fun createMeeting(user: User?, startTime: Long, endTime: Long, type: String, description: String, value: Int): Meeting? {
        if(user != null && user.token?.isNotBlank() == true &&( user.accountType == AccountType.ADMIN || (user.accountType == AccountType.LEAD && user.permissions.verifyAllAttendance))){
            println(user.token)
            return try {
                this.client.submitForm(url = ROUTES.CREATE_MEETING, formParameters = parameters {
                    append("createdBy", user.username)
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
            catch (e: Exception) {
                println("Issue with request")
                println(e.message)
                null }
        }
        println("issue with params")
        return null
    }

    suspend fun attendMeeting(
        user: User?,
        meetingId: String,
        tapTime: Long,
    ): User? {
        if(user != null && user.token?.isNotBlank() == true && user.accountType != AccountType.UNVERIFIED ) {
            return try {
                val response = this.client.submitForm(url = ROUTES.ATTEND_MEETING, formParameters = parameters {
                    append("userId", user._id)
                    append("meetingId", meetingId)
                    append("tapTime", tapTime.toString())
                }) {
                    header(HttpHeaders.Authorization, "Bearer ${user.token}")
                }.body<UserResponse>()
                User.fromSerializable(response)
            } catch(e: Exception) {
                println("Issue with request")
                println(e.message)
                null
            }
        }
        return null
    }
     suspend fun getTest(): ExamplePostResponse {
        return try {
            this.client.get("https://jsonplaceholder.typicode.com/posts/1").body()
        }
        catch(e: Exception) { ExamplePostResponse(
            userId = -1,
            id = -1,
            title = "ERROR",
            body = "ERROR"
        ) }
    }

//    suspend fun register(
//        username: String,
//        password: String,
//        email: String,
//        firstName: String,
//        lastName: String,
//        subteam: Subteam,
//        phone: Int,
//        grade: Int,
//    ): User? {
//
//    }
}