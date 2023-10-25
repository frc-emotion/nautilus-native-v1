package org.team2658.apikt.models

import kotlinx.serialization.Serializable

@Serializable
data class ChargedUpModel(
    val editHistory: List<EditHistory>,
    val _id: String,
    val competition: String,
    val matchNumber: Int,
    val teamNumber: Int,
    val teamName: String,
    val RPEarned: List<Boolean>,
    val totalRP: Int,
    val autoPeriod: ChargedUpScores,
    val teleopPeriod: ChargedUpScores,
    val coneRate: Double?,
    val cubeRate: Double?,
    val linkScore: Int,
    val autoDock: Boolean,
    val autoEngage: Boolean,
    val teleopDock: Boolean,
    val teleopEngage: Boolean,
    val parked: Boolean,
    val isDefensive: Boolean,
    val didBreak: Boolean,
    val penaltyCount: Int,
    val score: Int,
    val won: Boolean,
    val tied: Boolean,
    val comments: String,
)


@Serializable
data class EditHistory(
    val id: String,
//    val name: String?,
    val time: String
)

@Serializable
data class ChargedUpScores(
    val botScore: Int,
    val botCones: Int,
    val botCubes: Int,
    val midScore: Int,
    val midCones: Int,
    val midCubes: Int,
    val topScore: Int,
    val topCones: Int,
    val topCubes: Int,
    val totalScore: Int,
    val totalCones: Int,
    val totalCubes: Int,
    val coneRate: Double?,
    val cubeRate: Double?
) {
    constructor(
        botCones: Int,
        botCubes: Int,
        midCones: Int,
        midCubes: Int,
        topCones: Int,
        topCubes: Int
    ) : this(
        botScore = botCones + botCubes,
        botCones = botCones,
        botCubes = botCubes,
        midScore = midCones + midCubes,
        midCones = midCones,
        midCubes = midCubes,
        topScore = topCones + topCubes,
        topCones = topCones,
        topCubes = topCubes,
        totalScore = botCones + botCubes + midCones + midCubes + topCones + topCubes,
        totalCones = botCones + midCones + topCones,
        totalCubes = botCubes + midCubes + topCubes,
        coneRate = safeDivide((botCones + midCones + topCones), (botCones + botCubes + midCones + midCubes + topCones + topCubes)),
        cubeRate = safeDivide((botCubes + midCubes + topCubes), (botCones + botCubes + midCones + midCubes + topCones + topCubes))
    )
}

fun safeDivide(a: Int, b: Int): Double? {
    return if (b == 0) null else a.toDouble() / b.toDouble()
}