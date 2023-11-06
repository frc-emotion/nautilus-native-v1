package org.team2658.apikt

object ROUTES {
    const val BASE = "https://api.team2658.org/v2"
//    const val BASE = "http://Novas-macbook-pro.local:1234/v2"
//    const val BASE = "http://jasonmac2.local:1234/v2"
    const val LOGIN = "$BASE/users/login"
    const val REGISTER = "$BASE/users/register"
    const val CREATE_MEETING = "$BASE/attendance/createMeeting"
    const val ATTEND_MEETING = "$BASE/attendance/attendMeeting"
    const val ME = "$BASE/users/me"
    const val CHARGEDUP = "$BASE/chargedUp"
}