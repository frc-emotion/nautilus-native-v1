package org.team2658.nautilus.userauth

import kotlinx.serialization.Serializable
import org.team2658.nautilus.attendance.UserAttendance

sealed interface User {
    val _id: String
    val firstname: String
    val lastname: String
    val username: String
    val email: String
    val subteam: Subteam?
    val roles: List<String>
    val accountType: AccountType

    /**
     * Full user data, only available to admins via getUsers. Does not include the JWT
     */
    sealed interface Full: User {
        val accountUpdateVersion: Int
        val attendance: Map<String, UserAttendance>
        val grade: Int?
        val permissions: UserPermissions
        val phone: String?
    }

    sealed interface WithoutToken: User
}

/**
 * The user logged into the app. Includes a JWT for authentication and all user data.
 */
@Serializable
data class TokenUser(
    override val _id: String,
    override val firstname: String,
    override val lastname: String,
    override val username: String,
    override val email: String,
    override val subteam: Subteam? = null,
    override val roles: List<String>,
    override val accountType: AccountType,
    override val accountUpdateVersion: Int,
    override val attendance: Map<String, UserAttendance>,
    override val grade: Int? = null,
    override val permissions: UserPermissions,
    override val phone: String? = null,
    val token: String,
): User.Full

/**
 * A user with all of their data. Available to admins via getUsers or getUserById. No JWT.
 */
@Serializable
data class FullUser(
    override val _id: String,
    override val firstname: String,
    override val lastname: String,
    override val username: String,
    override val email: String,
    override val subteam: Subteam?,
    override val roles: List<String>,
    override val accountType: AccountType,
    override val accountUpdateVersion: Int,
    override val attendance: Map<String, UserAttendance>,
    override val grade: Int?,
    override val permissions: UserPermissions,
    override val phone: String?
): User.Full, User.WithoutToken

/**
 * A user with only the data that is available to all users. No JWT. Available to all users with base verification.
 */
@Serializable
data class PartialUser(
    override val _id: String,
    override val firstname: String,
    override val lastname: String,
    override val username: String,
    override val email: String,
    override val subteam: Subteam?,
    override val roles: List<String>,
    override val accountType: AccountType,
): User, User.WithoutToken

fun isAdmin(user: User?) = user != null && user.accountType.value >= AccountType.ADMIN.value

fun isLead(user: User?) = user != null && user.accountType.value >= AccountType.LEAD.value