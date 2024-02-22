package org.nautilusapp.nautilus.scouting.scoutingdata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crescendo(
    override val _id: String,
    override val competition: String,
    override val teamNumber: Int,
    override val teamName: String,
    override val matchNumber: Int,
    @SerialName("score") override val finalScore: Int,
    override val penaltyPointsEarned: Int,
    override val won: Boolean,
    override val tied: Boolean,
    override val comments: String,
    override val defensive: Boolean,
    override val brokeDown: Boolean,
    override val rankingPoints: Int,
    val auto: CrescendoAuto,
    val teleop: CrescendoTeleop,
    val stage: CrescendoStage,
    val ranking: CrescendoRankingPoints,
    val createdBy: String,
) : ScoutingData

@Serializable
data class CrescendoRequestBody(
    val auto: CrescendoAuto,
    override val comments: String?,
    override val competition: String,
    override val defensive: Boolean,
    override val matchNumber: Int,
    override val penaltyPointsEarned: Int,
    override val ranking: CrescendoRankingPoints,
    override val rankingPoints: Int,
    override val score: Int,
    val stage: CrescendoStage,
    override val teamNumber: Int,
    val teleop: CrescendoTeleop,
    override val tied: Boolean,
    override val won: Boolean,
    override val brokeDown: Boolean,
) : ScoutingSubmission

@Serializable
data class CrescendoAuto(
    val leave: Boolean,
    val ampNotes: Int,
    val speakerNotes: Int
)

@Serializable
data class CrescendoTeleop(
    val ampNotes: Int,
    val speakerUnamped: Int,
    val speakerAmped: Int,
)

@Serializable
data class CrescendoStage(
    val state: CrescendoStageState,
    val harmony: Int,
    val trapNotes: Int,
)

@Serializable
enum class CrescendoStageState {
    @SerialName("NOT_PARKED")
    NOT_PARKED,

    @SerialName("PARKED")
    PARKED,

    @SerialName("ONSTAGE")
    ONSTAGE,

    @SerialName("ONSTAGE_SPOTLIT")
    ONSTAGE_SPOTLIT
}

@Serializable
data class CrescendoRankingPoints(
    val melody: Boolean,
    val ensemble: Boolean,
) : RP(rp1 = melody, rp2 = ensemble)