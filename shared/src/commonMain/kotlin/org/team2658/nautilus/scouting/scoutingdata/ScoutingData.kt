package org.team2658.nautilus.scouting.scoutingdata

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
