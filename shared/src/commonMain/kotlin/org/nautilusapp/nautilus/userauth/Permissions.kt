package org.nautilusapp.nautilus.userauth

import kotlinx.serialization.Serializable

@Serializable
data class UserPermissions(
    val generalScouting: Boolean,
    val pitScouting: Boolean,
    val viewMeetings: Boolean,
    val viewScoutingData: Boolean,
    val blogPosts: Boolean,
    val deleteMeetings: Boolean,
    val makeAnnouncements: Boolean,
    val makeMeetings: Boolean,
) {
    constructor() :
            this(false,
                false,
                false,
                false,
                false,
                false,
                false,
                false)

 }
