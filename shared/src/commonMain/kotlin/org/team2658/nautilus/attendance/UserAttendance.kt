package org.team2658.nautilus.attendance

import kotlinx.serialization.Serializable

@Serializable
data class UserAttendance(
    val totalHoursLogged: Int,
    val completedMarketingAssignment: Boolean,
    val logs: List<String>
)
