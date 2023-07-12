package org.team2658.emotion.userauth

data class UserPermissions(
    val submitScoutingData: Boolean = false,
    val viewScoutingData: Boolean = false,
    val inPitScouting: Boolean = false,
    //TODO: add more here when API is done
    )
