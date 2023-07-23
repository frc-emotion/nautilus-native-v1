package org.team2658.emotion.attendance

data class UserAttendance(
    val totalHoursLogged: Double,
    val meetingHoursLogged: Double,
    val volunteeringHoursLogged: Double,
    val competitionHoursLogged: Double,
    val logs: List<AttendanceLog> = listOf()
)
