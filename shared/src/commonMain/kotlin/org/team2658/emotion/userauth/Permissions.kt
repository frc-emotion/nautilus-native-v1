package org.team2658.emotion.userauth

import org.team2658.emotion.KeyValue
import org.team2658.emotion.setKeyValueListItem

data class UserPermissions(
    val standScouting: Boolean = false,
    val viewScoutingData: Boolean = false,
    val inPitScouting: Boolean = false,
    val makeBlogPosts: Boolean = false,
    val verifySubteamAttendance: Boolean = false,
    val verifyAllAttendance: Boolean = false,
    val makeAnnouncements: Boolean = false,
) {
    fun asList(): List<KeyValue<Boolean>> {
        return listOf(
            KeyValue("Stand Scouting", standScouting),
            KeyValue("View Scouting Data", viewScoutingData),
            KeyValue("In-Pit Scouting", inPitScouting),
            KeyValue("Make Blog Posts", makeBlogPosts),
            KeyValue("Verify Subteam Attendance", verifySubteamAttendance),
            KeyValue("Verify All Attendance", verifyAllAttendance),
            KeyValue("Make Announcements", makeAnnouncements),
        )
    }

    companion object {
        fun fromList(list: List<KeyValue<Boolean>>): UserPermissions {
            return UserPermissions(
                standScouting = list[0].value,
                viewScoutingData = list[1].value,
                inPitScouting = list[2].value,
                makeBlogPosts = list[3].value,
                verifySubteamAttendance = list[4].value,
                verifyAllAttendance = list[5].value,
                makeAnnouncements = list[6].value,
            )
        }

        fun adminPermissions(): UserPermissions {
            return UserPermissions(
                standScouting = true,
                viewScoutingData = true,
                inPitScouting = true,
                makeBlogPosts = true,
                verifySubteamAttendance = true,
                verifyAllAttendance = true,
                makeAnnouncements = true,
            )
        }
    }
}


fun getPermissions(user: User): UserPermissions {

    if(user.accountType == AccountType.ADMIN || user.accountType == AccountType.SUPERUSER) return UserPermissions.adminPermissions()

    val permissions = UserPermissions().asList().toMutableList()

    for (role in user.roles) {
        for (permission in role.permissions.asList()) {
            if (permission.value) {
                setKeyValueListItem(permission.key, permissions, true)
            }
        }
    }
    return UserPermissions.fromList(permissions)
}