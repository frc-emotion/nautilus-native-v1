package org.team2658.emotion.android.screens.scouting.standscoutingforms

data class ScoutingData(
    val competition: String,
    val teamNumber: Int,
    val matchNumber: Int,
    val finalScore: Int,
    val penaltyPointsEarned: Int,
    val won: Boolean,
    val tied: Boolean,
    val comments: String,
    val defensive: Boolean,
    val brokeDown: Boolean,
)
