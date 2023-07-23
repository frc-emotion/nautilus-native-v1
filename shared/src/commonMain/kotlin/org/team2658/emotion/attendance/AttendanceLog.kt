package org.team2658.emotion.attendance

import org.team2658.emotion.userauth.User

data class AttendanceLog(
    val name: String,
    val type: MeetingType,

    val tapInTime: Int, //unix timestamp
    val tapOutTime: Int,
    val tappedBy: User?,
    val verified: Boolean = tappedBy != null
)
