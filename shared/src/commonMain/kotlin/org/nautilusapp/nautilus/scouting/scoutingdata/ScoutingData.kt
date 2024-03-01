package org.nautilusapp.nautilus.scouting.scoutingdata

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
    val ranking: RP
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

//@Serializable
//abstract class RP(
//    private val rp1: Boolean,
//    private val rp2: Boolean
//)
//
interface RP {
    val rp1: Boolean
    val rp2: Boolean
}