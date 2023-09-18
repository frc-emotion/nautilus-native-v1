package org.team2658.apikt.responses

import kotlinx.serialization.Serializable

//response from https://jsonplaceholder.typicode.com/posts/1 (GET)
@Serializable
data class ExamplePostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
