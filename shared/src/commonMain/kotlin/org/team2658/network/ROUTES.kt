package org.team2658.network

object ROUTES {
    const val BASE = "https://staging.team2658.org/v2"
    const val LOGIN = "$BASE/users/login"
    const val REGISTER = "$BASE/users/register"
    const val CREATE_MEETING = "$BASE/attendance/createMeeting"
    const val ATTEND_MEETING = "$BASE/attendance/attendMeeting"
    const val ME = "$BASE/users/me"
    const val CHARGEDUP = "$BASE/chargedUp"
}