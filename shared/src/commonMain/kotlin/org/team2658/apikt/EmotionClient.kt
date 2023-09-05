package org.team2658.apikt

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.team2658.apikt.responses.ExamplePostResponse
import org.team2658.apikt.responses.UserResponse
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