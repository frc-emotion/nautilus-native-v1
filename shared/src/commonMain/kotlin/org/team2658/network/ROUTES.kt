package org.team2658.network

data class Routes(val base: String) {
    val users = "$base/users"
    val login = "$base/users/login"
    val register = "$base/users/register"
    val meetings = "$base/attendance/meetings"
    val me = "$base/users/me"
}