package org.team2658.nautilus.userauth

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team2658.nautilus.attendance.UserAttendance
import org.team2658.network.models.RoleModel
import org.team2658.network.models.RolePermissionsModel
import org.team2658.network.models.UserModel

data class User(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val token: String? = null,
    val subteam: Subteam,
    val grade: Int,
    val roles: List<Role>,
    val accountType: AccountType = AccountType.UNVERIFIED,
    val accountUpdateVersion: Int = 1,
    val socials: List<Social> = listOf(),

    val attendance: Map<String,UserAttendance>,
) {
    val permissions = getPermissions(this)
    val isAdminOrLead = this.accountType.value >= AccountType.LEAD.value
    val isAdmin = this.accountType.value >= AccountType.ADMIN.value

    companion object {
        fun fromSerializable(usr: UserModel): User {
            return User(
                firstName = usr.firstname,
                lastName = usr.lastname,
                username = usr.username,
                email = usr.email,
                phoneNumber = usr.phone?: "",
                token = usr.token,
//                subteam = when(usr.subteam?.lowercase()) {
//                    "software" -> Subteam.SOFTWARE
//                    "build" -> Subteam.BUILD
//                    "marketing" -> Subteam.MARKETING
//                    "electrical" -> Subteam.ELECTRICAL
//                    "design" -> Subteam.DESIGN
//                    "executive" -> Subteam.EXECUTIVE
//                    else -> Subteam.NONE
//                },
                subteam = try {
                    Subteam.valueOf(usr.subteam?.trim()?.uppercase() ?: "NONE")
                }catch (_: Exception) { Subteam.NONE },
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
                accountType = AccountType.of(usr.accountType),
                attendance = usr.attendance,
                _id = usr._id,
            )
        }

        fun fromJSON(json: String?): User? {
            return json?.let { try {
                val usr = Json.decodeFromString<UserModel>(json)
                fromSerializable(usr)
            }catch(e: Exception) { null } }
        }

        fun authState(usr: User?): AuthState {
            return when(usr?.accountType) {
                null -> AuthState.NOT_LOGGED_IN
                AccountType.SUPERUSER, AccountType.ADMIN, AccountType.LEAD, AccountType.BASE -> AuthState.LOGGED_IN
                AccountType.UNVERIFIED -> AuthState.AWAITING_VERIFICATION
            }
        }
    }
    private fun toSerializable(): UserModel {
        return UserModel(
            firstname = this.firstName,
            _id = this._id,
            lastname = this.lastName,
            username = this.username,
            email = this.email,
            phone = this.phoneNumber,
            token = this.token,
            subteam = this.subteam.name.lowercase(),
            grade = this.grade,
            roles = this.roles.map{ RoleModel(
                name = it.name,
                permissions = RolePermissionsModel(
                    verifyAllAttendance = it.permissions.verifyAllAttendance,
                    verifySubteamAttendance = it.permissions.verifySubteamAttendance,
                    makeAnnouncements = it.permissions.makeAnnouncements,
                    makeBlogPosts = it.permissions.makeBlogPosts,
                    inPitScouting = it.permissions.inPitScouting,
                    standScouting = it.permissions.standScouting,
                    viewScoutingData = it.permissions.viewScoutingData
                )
            )},
            accountType = this.accountType.value,
            attendance = this.attendance,
        )
    }

    fun toJSON(): String {
        return Json.encodeToString(this.toSerializable())
    }
}




