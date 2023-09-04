package org.team2658.apikt.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val _id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val phone: Int? = null,
    val token: String? = null,
    val subteam: String? = null,
    val grade: Int? = null,
    val roles: Array<RoleResponse>? = null,
    val accountType: Int? = null, // isAdmin && isVerified are NOT supported. This client was developed for API v2.0.0 and later
//    val accountUpdateVersion: Int? = null,
//    val socials: Array<String>? = null,
//    val parents: Array<User>? = null,
//    val attendance: Array<Attendance>? = null,
//    val children: Array<User>? = null,
//    val spouse: User? = null,
//    val donationAmounts: Array<Double>? = null,
//    val employer: Employer? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UserResponse

        if (_id != other._id) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (token != other.token) return false
        if (subteam != other.subteam) return false
        if (grade != other.grade) return false
        if (roles != null) {
            if (other.roles == null) return false
            if (!roles.contentEquals(other.roles)) return false
        } else if (other.roles != null) return false
        if (accountType != other.accountType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + (phone ?: 0)
        result = 31 * result + (token?.hashCode() ?: 0)
        result = 31 * result + (subteam?.hashCode() ?: 0)
        result = 31 * result + (grade ?: 0)
        result = 31 * result + (roles?.contentHashCode() ?: 0)
        result = 31 * result + (accountType ?: 0)
        return result
    }
}
