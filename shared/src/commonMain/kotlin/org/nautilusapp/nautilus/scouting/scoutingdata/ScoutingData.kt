package org.nautilusapp.nautilus.scouting.scoutingdata

import kotlinx.serialization.Serializable

interface ScoutingData {
    val competition: String
    val teamNumber: Int
    val teamName: String
    val matchNumber: Int
    val finalScore: Int
    val penaltyPointsEarned: Int
    val won: Boolean
    val tied: Boolean
    val comments: String
    val defensive: Boolean
    val brokeDown: Boolean
    val rankingPoints: Int
    val _id: String
}

interface ScoutingSubmission {
    val comments: String?
    val competition: String
    val defensive: Boolean
    val matchNumber: Int
    val penaltyPointsEarned: Int
    val rankingPoints: Int
    val ranking: RP
    val score: Int
    val teamNumber: Int
    val tied: Boolean
    val won: Boolean
    val brokeDown: Boolean
}

@Serializable
abstract class RP(
    protected val rp1: Boolean,
    protected val rp2: Boolean
)

