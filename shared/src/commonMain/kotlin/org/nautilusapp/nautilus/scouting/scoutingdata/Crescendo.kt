package org.nautilusapp.nautilus.scouting.scoutingdata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
    override val ranking: CrescendoRankingPoints,
    val createdBy: String,
) : ScoutingData {
    companion object {
        fun exampleData(): Crescendo {
            val tn = (0..50).random()
            return Crescendo(
                _id = String.random(),
                competition = comps.random(),
                teamNumber = tn,
                teamName = "team$tn",
                matchNumber = (0..6).random(),
                finalScore = (0..200).random(),
                penaltyPointsEarned = (0..50).random(),
                won = Random.nextBoolean(),
                tied = Random.nextBoolean(),
                comments = String.random(80),
                defensive = Random.nextBoolean(),
                brokeDown = Random.nextBoolean(),
                rankingPoints = (0..4).random(),
                auto = CrescendoAuto(
                    leave = Random.nextBoolean(),
                    ampNotes = (0..6).random(),
                    speakerNotes = (0..6).random()
                ),
                teleop = CrescendoTeleop(
                    ampNotes = (0..50).random(),
                    speakerUnamped = (0..50).random(),
                    speakerAmped = (0..50).random()
                ),
                stage = CrescendoStage(
                    state = CrescendoStageState.entries.random(),
                    harmony = (0..50).random(),
                    trapNotes = (0..50).random()
                ),
                ranking = CrescendoRankingPoints(
                    melody = Random.nextBoolean(),
                    ensemble = Random.nextBoolean()
                ),
                createdBy = String.random()
            )
        }
    }
}

private fun String.Companion.random(len: Int = 16): String {
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('!'..'@')
    return (1..len)
        .map { chars.random() }
        .joinToString("")
}

private val comps = listOf(
    "meow",
    "woof",
    "moo",
    "oink",
    "baa",
)

@Serializable
data class CrescendoSubmission(
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
) : RP {
    override val rp1 = melody
    override val rp2 = ensemble
}