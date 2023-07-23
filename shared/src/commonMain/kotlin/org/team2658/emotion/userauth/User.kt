package org.team2658.emotion.userauth

import org.team2658.emotion.attendance.UserAttendance

data class User(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: Int,
    val token: String? = null,
    val subteam: Subteam,
    val grade: Int,
    val roles: List<Role>,
    val accountType: AccountType = AccountType.UNVERIFIED,
    val accountUpdateVersion: Int = 1,
    val socials: List<Social> = listOf(),

    //student accounts
    val parents: List<User>? = null,
    val attendance: List<UserAttendance>? = null,

    //parent accounts
    val children: List<User>? = null,
    val spouse: User? = null,
    val donationAmounts: List<Double>? = null,
    val employer: Employer? = null,
) {
    val permissions = getPermissions(this)
}




