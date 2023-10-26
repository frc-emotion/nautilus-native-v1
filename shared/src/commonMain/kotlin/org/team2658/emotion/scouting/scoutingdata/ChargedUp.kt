package org.team2658.emotion.scouting.scoutingdata

import org.team2658.apikt.models.ChargedUpScores
import org.team2658.emotion.scouting.GameResult

data class ChargedUp(
    val baseData: ScoutingData,
    val autoPeriod: ChargedUpScores,
    val teleopPeriod: ChargedUpScores,
    val linkScore: Int,
    val autoDock: Boolean,
    val autoEngage: Boolean,
    val teleopDock: Boolean,
    val teleopEngage: Boolean,
    val parked: Boolean,
    val RPEarned: List<Boolean>, //[Sustain, Activation]
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
) {
    val totalRP = getChargedUpRP(this.RPEarned[0], this.gameResult, this.RPEarned[1])
}

private fun getChargedUpRP (sustain: Boolean, gameResult: GameResult, activation: Boolean): Int {
    var out = 0
    if(sustain) out++
    if(activation) out++
    out += when(gameResult){
        GameResult.LOSS -> 0
        GameResult.TIE -> 1
        GameResult.WIN -> 2
        }
    return out
}
