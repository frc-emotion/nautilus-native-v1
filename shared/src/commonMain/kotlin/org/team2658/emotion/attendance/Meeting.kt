package org.team2658.emotion.attendance

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Meeting(
    val _id: String,
    val startTime: Long, // Unix timestamp
    val endTime: Long, // Unix timestamp
    val type: String,
    val description: String,
    val value: Int,
    val createdBy: String,
) {
    companion object {
        fun fromJSON(json: String?): Meeting? {
            return json?.let {
                try {
                    Json.decodeFromString<Meeting>(json)
                } catch(e: Exception) { null }
            }
        }
    }
    fun toJson(): String? {
        return try {
            Json.encodeToString(this)
        } catch(e: Exception) { null }
    }
}
