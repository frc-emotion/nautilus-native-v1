package org.team2658.nautilus.attendance

import kotlinx.serialization.Serializable

@Serializable
data class UserAttendance(
    val totalHoursLogged: Int,
    val logs: List<MeetingLog>
)

@Serializable
data class MeetingLog(
    val meetingId: String,
    val verifiedBy: String = "unknown",
)