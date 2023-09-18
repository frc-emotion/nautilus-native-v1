package org.team2658.emotion.scouting.scoutingdata

data class RapidReact(
    val baseData: ScoutingData,
    val leftTarmac: Boolean,
    val autoLower: Int,
    val autoUpper: Int,
    val teleopLower: Int,
    val teleopUpper: Int,
    val cycleTime: String,
    val shotLocation: String,
    val climbScore: Int,
    val humanShot: Boolean,
    val cargoRP: Boolean,
    val hangarRP: Boolean,
) : ScoutingData(
    competition = baseData.competition,
    matchNumber = baseData.matchNumber,
    comments = baseData.comments,
    defensive = baseData.defensive,
    finalScore = baseData.finalScore,
    gameResult = baseData.gameResult,
    teamNumber = baseData.teamNumber,
    penaltyPointsEarned = baseData.penaltyPointsEarned,
    brokeDown = baseData.brokeDown,
)
