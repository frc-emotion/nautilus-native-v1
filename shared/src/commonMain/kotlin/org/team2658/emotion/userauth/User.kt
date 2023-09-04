package org.team2658.emotion.userauth

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team2658.apikt.responses.RolePermissionsResponse
import org.team2658.apikt.responses.RoleResponse
import org.team2658.apikt.responses.UserResponse
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
    companion object {
        fun fromSerializable(usr: UserResponse): User {
            return User(
                firstName = usr.firstname,
                lastName = usr.lastname,
                username = usr.username,
                email = usr.email,
                phoneNumber = usr.phone?: -1,
                token = usr.token,
                subteam = when(usr.subteam) {
                    "software" -> Subteam.SOFTWARE
                    "build" -> Subteam.BUILD
                    "marketing" -> Subteam.MARKETING
                    "electrical" -> Subteam.ELECTRICAL
                    "design" -> Subteam.DESIGN
                    "executive" -> Subteam.EXECUTIVE
                    else -> Subteam.NONE
                },
                grade = usr.grade?: -1,
                roles = usr.roles?.map { Role(it.name, UserPermissions(
                    verifyAllAttendance = it.permissions.verifyAllAttendance,
                    verifySubteamAttendance = it.permissions.verifySubteamAttendance,
                    makeAnnouncements = it.permissions.makeAnnouncements,
                    makeBlogPosts = it.permissions.makeBlogPosts,
                    inPitScouting = it.permissions.inPitScouting,
                    standScouting = it.permissions.standScouting,
                    viewScoutingData = it.permissions.viewScoutingData
                )) } ?: listOf(),
                accountType = when(usr.accountType) {
                    1 -> AccountType.BASE
                    2 -> AccountType.LEAD
                    3 -> AccountType.ADMIN
                    else -> AccountType.UNVERIFIED
                }
            )
        }

        fun fromJSON(json: String?): User? {
            return json?.let { try {
                val usr = Json.decodeFromString<UserResponse>(json)
                fromSerializable(usr)
            }catch(e: Exception) { null } }
        }
    }
    fun toSerializable(): UserResponse {
        return UserResponse(
            firstname = this.firstName,
            _id = "",
            lastname = this.lastName,
            username = this.username,
            email = this.email,
            phone = this.phoneNumber,
            token = this.token,
            subteam = this.subteam.name.lowercase(),
            grade = this.grade,
            roles = this.roles.map{ RoleResponse(
                name = it.name,
                permissions = RolePermissionsResponse(
                    verifyAllAttendance = it.permissions.verifyAllAttendance,
                    verifySubteamAttendance = it.permissions.verifySubteamAttendance,
                    makeAnnouncements = it.permissions.makeAnnouncements,
                    makeBlogPosts = it.permissions.makeBlogPosts,
                    inPitScouting = it.permissions.inPitScouting,
                    standScouting = it.permissions.standScouting,
                    viewScoutingData = it.permissions.viewScoutingData
                )
            )}.toTypedArray(),
            accountType = when(this.accountType) {
                AccountType.BASE -> 1
                AccountType.LEAD -> 2
                AccountType.ADMIN -> 3
                AccountType.UNVERIFIED -> 0
            }
        )
    }

    fun toJSON(): String {
        return Json.encodeToString(this.toSerializable())
    }
}




