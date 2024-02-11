package org.team2658.localstorage

import org.team2658.nautilus.attendance.MeetingLog
import org.team2658.nautilus.attendance.UserAttendance
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.FullUser
import org.team2658.nautilus.userauth.PartialUser
import org.team2658.nautilus.userauth.Subteam
import org.team2658.nautilus.userauth.TokenUser
import org.team2658.nautilus.userauth.User
import org.team2658.nautilus.userauth.UserPermissions

typealias UserIDInfo = GetInfoById

class UsersDB(db: AppDatabase) {
    private val users = db.usersQueries

    private fun insertUser(user: User) {
        if(user is User.WithoutToken && user._id == users.getLoggedInUser().executeAsOneOrNull()?.id) return
        if(user is TokenUser) users.deleteLoggedIn()

        users.insertBase(
            username = user.username,
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            accountType = user.accountType.value,
            id = user._id,
            subteam = user.subteam?.name?.trim()?.lowercase()?: "none",
        )

        user.roles.forEach {
            users.insertRole(
                id = user._id,
                name = it,
            )
        }

       if (user is User.Full) {
           users.insertExtras(
            id = user._id,
            accountUpdateVersion = user.accountUpdateVersion,
            grade = user.grade,
            phone = user.phone,
            permissions_generalScouting = user.permissions.generalScouting,
            permissions_pitScouting = user.permissions.pitScouting,
            permissions_viewMeetings = user.permissions.viewMeetings,
            permissions_viewScoutingData = user.permissions.viewScoutingData,
            permissions_blogPosts = user.permissions.blogPosts,
            permissions_deleteMeetings = user.permissions.deleteMeetings,
            permissions_makeAnnouncements = user.permissions.makeAnnouncements,
            permissions_makeMeetings = user.permissions.makeMeetings,
            isLogin = user is TokenUser,
               )
           user.attendance.entries.forEach { (k, v) ->
               users.insertAttendance(
                   id = user._id,
                   attendance_period = k,
                   total_hours = v.totalHoursLogged,
               )
                v.logs.forEach {
                     users.insertAttendanceLog(
                         id = user._id,
                         meeting_id = it.meetingId,
                         verified_by = it.verifiedBy,
                         attendance_period = k
                     )
                }
           }
        }
    }

    private fun getAttendance(id: String) = users
        .getUserAttendance(id)
        .executeAsList().associate {
            val logs = users
                .getUserAttendanceLogs(id, it.attendance_period)
                .executeAsList()
                .map { mLog ->
                    MeetingLog(
                        meetingId = mLog.meeting_id,
                        verifiedBy = mLog.verified_by
                    )
                }
            val att = UserAttendance(
                totalHoursLogged = it.total_hours,
                logs = logs,
            )
            Pair(it.attendance_period, att)
        }

    private fun extractUser(user: User_table): User.WithoutToken {
        val roles = users.getUserRoles(user.id).executeAsList()
        val extras = users.getExtras(user.id).executeAsOneOrNull()
            ?: return PartialUser( //if no extras, return a partial user
                _id = user.id,
                firstname = user.firstname,
                lastname = user.lastname,
                username = user.username,
                email = user.email,
                subteam = try {
                    Subteam.valueOf(user.subteam.trim().uppercase())
                } catch (_: Exception) {
                    Subteam.NONE
                },
                roles = roles,
                accountType = AccountType.of(user.accountType),
            )
        val attendance = getAttendance(user.id)
        return FullUser(
            _id = user.id,
            firstname = user.firstname,
            lastname = user.lastname,
            username = user.username,
            email = user.email,
            subteam = when(user.subteam.trim().lowercase()) {
                "software" -> Subteam.SOFTWARE
                "electrical" -> Subteam.ELECTRICAL
                "build" -> Subteam.BUILD
                "marketing" -> Subteam.MARKETING
                "design" -> Subteam.DESIGN
                "executive" -> Subteam.EXECUTIVE
                else -> Subteam.NONE
            },
            roles = roles,
            accountType = AccountType.of(user.accountType),
            accountUpdateVersion = extras.accountUpdateVersion,
            attendance = attendance,
            grade = extras.grade,
            permissions = UserPermissions(
                generalScouting = extras.permissions_generalScouting,
                pitScouting = extras.permissions_pitScouting,
                viewMeetings = extras.permissions_viewMeetings,
                viewScoutingData = extras.permissions_viewScoutingData,
                blogPosts = extras.permissions_blogPosts,
                deleteMeetings = extras.permissions_deleteMeetings,
                makeAnnouncements = extras.permissions_makeAnnouncements,
                makeMeetings = extras.permissions_makeMeetings,
            ),
            phone = extras.phone,
        )
    }

    fun insertUsers(ls: List<User.WithoutToken>) {
        users.transaction {
            ls.forEach { insertUser(it) }
        }
    }


    fun getUsers(): List<User.WithoutToken> {
        return users
            .getBaseUsers()
            .executeAsList()
            .map(::extractUser)
    }

    fun getLoggedInUser(token: String): TokenUser? {
        return try {
            users.getLoggedInUser().executeAsOneOrNull()?.let { usr ->
                val roles = users.getUserRoles(usr.id).executeAsList()
                val attendance = getAttendance(usr.id)
                TokenUser(
                    _id = usr.id,
                    firstname = usr.firstname,
                    lastname = usr.lastname,
                    username = usr.username,
                    email = usr.email,
                    subteam = when(usr.subteam.trim().lowercase()) {
                        "software" -> Subteam.SOFTWARE
                        "electrical" -> Subteam.ELECTRICAL
                        "build" -> Subteam.BUILD
                        "marketing" -> Subteam.MARKETING
                        "design" -> Subteam.DESIGN
                        "executive" -> Subteam.EXECUTIVE
                        else -> Subteam.NONE
                    },
                    roles = roles,
                    accountType = AccountType.of(usr.accountType),
                    accountUpdateVersion = usr.accountUpdateVersion,
                    attendance = attendance,
                    grade = usr.grade,
                    permissions = UserPermissions(
                        generalScouting = usr.permissions_generalScouting,
                        pitScouting = usr.permissions_pitScouting,
                        viewMeetings = usr.permissions_viewMeetings,
                        viewScoutingData = usr.permissions_viewScoutingData,
                        blogPosts = usr.permissions_blogPosts,
                        deleteMeetings = usr.permissions_deleteMeetings,
                        makeAnnouncements = usr.permissions_makeAnnouncements,
                        makeMeetings = usr.permissions_makeMeetings,
                    ),
                    phone = usr.phone,
                    token = token,
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateLoggedInUser(user: TokenUser, setToken: (String?) -> Unit): TokenUser? {
        return try {
        users.transaction {
            users.deleteLoggedIn()
            insertUser(user)
        }
        setToken(user.token)
        getLoggedInUser(user.token)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteUser(user: User) {
        users.deleteOne(user._id)
    }

    fun clearUsers() {
        users.deleteNotLoggedIn()
    }

    fun logoutUser(setToken: (String?) -> Unit) {
        users.deleteLoggedIn()
        setToken(null)
    }
}