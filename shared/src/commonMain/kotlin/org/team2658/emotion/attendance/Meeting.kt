package org.team2658.emotion.attendance

import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val _id: String,
    val startTime: Long, // Unix timestamp
    val endTime: Long, // Unix timestamp
    val type: String,
    val description: String,
    val value: Int,
    val createdBy: String,
)
