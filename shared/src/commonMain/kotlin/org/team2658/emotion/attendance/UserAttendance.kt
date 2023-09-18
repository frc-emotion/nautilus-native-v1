package org.team2658.emotion.attendance

import kotlinx.serialization.Serializable

@Serializable
data class UserAttendance(
    val totalHoursLogged: Int,
    val completedMarketingAssignment: Boolean,
    val logs: List<String>
)
