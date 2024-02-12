package org.nautilusapp.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

typealias Message = String?

@Serializable
data class ServerMessage(
    val message: String
) {
    companion object {
        fun readMessage(json: String?): Message {
            return try {
                val mes = Json.decodeFromString<ServerMessage>(json ?: "")
                mes.message
            } catch(e: Exception) {
                null
            }
        }
    }
}