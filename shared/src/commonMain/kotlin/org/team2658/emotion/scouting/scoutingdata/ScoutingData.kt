package org.team2658.emotion.scouting.scoutingdata

import org.team2658.emotion.scouting.GameResult

sealed class ScoutingData(
    val competition: String,
    val teamNumber: Int,
    val matchNumber: Int,
    val finalScore: Int,
    val penaltyPointsEarned: Int,
    val gameResult: GameResult,
    val comments: String,
    val defensive: Boolean,
)
