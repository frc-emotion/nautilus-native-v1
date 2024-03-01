package org.nautilusapp.nautilus.scouting.analysis.models.base

interface AverageData {
    val score: Double
    val penalty: Double
    val winRate: Double
    val tieRate: Double
    val comments: List<String>
    val defensiveRate: Double
    val breakdownRate: Double
    val rpAverage: Double //average when aggregating at match level, totalled when aggregating across a competition
    val rpTotal: Double //average when aggregating at match level, totalled when aggregating across a competition
    val rp1Rate: Double
    val rp2Rate: Double
}