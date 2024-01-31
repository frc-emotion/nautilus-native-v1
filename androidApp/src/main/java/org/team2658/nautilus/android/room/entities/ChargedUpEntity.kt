package org.team2658.nautilus.android.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.team2658.network.ChargedUpRequestParams
import org.team2658.network.models.ChargedUpScores

@Entity(tableName = "chargedups")
data class ChargedUpEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val competition: String,
    val matchNumber: Int,
    val teamNumber: Int,
    val sustainRP: Boolean,
    val activationRP: Boolean,
    val totalRP: Int,
    val autoBotCones: Int,
    val autoBotCubes: Int,
    val autoMidCones: Int,
    val autoMidCubes: Int,
    val autoTopCones: Int,
    val autoTopCubes: Int,
    val teleopBotCones: Int,
    val teleopBotCubes: Int,
    val teleopMidCones: Int,
    val teleopMidCubes: Int,
    val teleopTopCones: Int,
    val teleopTopCubes: Int,
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
    val storageType: StorageType,
)

enum class StorageType {
    TEMP,
    LONG_TERM,
}

fun chargedUpParamsFromEntity(data: ChargedUpEntity): ChargedUpRequestParams {
    return ChargedUpRequestParams(
        penaltyCount = data.penaltyCount,
        competition = data.competition,
        isDefensive = data.isDefensive,
        linkScore = data.linkScore,
        matchNumber = data.matchNumber,
        didBreak = data.didBreak,
        teleopEngage = data.teleopEngage,
        teleopDock = data.teleopDock,
        parked = data.parked,
        autoEngage = data.autoEngage,
        autoDock = data.autoDock,
        teleopPeriod = ChargedUpScores(
            topCubes = data.teleopTopCubes,
            topCones = data.teleopTopCones,
            botCones = data.teleopBotCones,
            botCubes = data.teleopBotCubes,
            midCones = data.teleopMidCones,
            midCubes = data.teleopMidCubes,
        ),
        autoPeriod = ChargedUpScores(
            topCubes = data.autoTopCubes,
            topCones = data.autoTopCones,
            botCones = data.autoBotCones,
            botCubes = data.autoBotCubes,
            midCones = data.autoMidCones,
            midCubes = data.autoMidCubes,
        ),
        teamNumber = data.teamNumber,
        totalRP = data.totalRP,
        RPEarned = listOf(data.sustainRP, data.activationRP),
        comments = data.comments,
        score = data.score,
        tied = data.tied,
        won = data.won,
    )
}
