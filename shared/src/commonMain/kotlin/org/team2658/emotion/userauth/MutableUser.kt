package org.team2658.emotion.userauth

import org.team2658.emotion.attendance.UserAttendance

data class MutableUser(
    var _id: String,
    var firstName: String,
    var lastName: String,
    var username: String,
    var email: String,
    var phoneNumber: String,
    var token: String? = null,
    var subteam: Subteam,
    var grade: Int,
    var roles: List<Role>,
    var accountType: AccountType = AccountType.UNVERIFIED,
    var accountUpdateVersion: Int = 1,
    var socials: List<Social> = listOf(),

    //student accounts
    var parents: List<User>? = null,
    var attendance: List<UserAttendance>,

    //parent accounts
    var children: List<User>? = null,
    var spouse: User? = null,
    var donationAmounts: List<Double>? = null,
    var employer: Employer? = null,

    var customRoleMessage: String? = null
) {
    var permissions = getPermissions(this.toImmutable())
    var isAdminOrLead = this.accountType.value >= AccountType.LEAD.value
    var isAdmin = this.accountType.value >= AccountType.ADMIN.value

    fun toImmutable(): User {
        val user = this
        return User(user._id, user.firstName, user.lastName, user.username, user.email, user.phoneNumber, user.token, user.subteam, user.grade, user.roles, user.accountType, user.accountUpdateVersion, user.socials, user.parents, user.attendance, user.children, user.spouse, user.donationAmounts, user.employer, user.customRoleMessage)
    }
}
