package org.team2658.apikt.responses

import kotlinx.serialization.Serializable

@Serializable
data class RolePermissionsResponse(
    val standScouting: Boolean,
    val inPitScouting: Boolean,
    val viewScoutingData: Boolean,
    val makeBlogPosts: Boolean,
    val verifySubteamAttendance: Boolean,
    val verifyAllAttendance: Boolean,
    val makeAnnouncements: Boolean,
)
