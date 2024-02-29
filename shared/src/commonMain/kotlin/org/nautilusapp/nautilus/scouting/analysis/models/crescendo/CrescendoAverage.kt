package org.nautilusapp.nautilus.scouting.analysis.models.crescendo

import org.nautilusapp.nautilus.scouting.analysis.models.base.AverageData

data class CrescendoAverage(
    override val score: Double,
    override val penalty: Double,
    override val winRate: Double,
    override val tieRate: Double,
    override val comments: List<String>,
    override val defensiveRate: Double,
    override val breakdownRate: Double,
    override val rpAverage: Double,
    override val rpTotal: Double,
    override val rp1Rate: Double,
    override val rp2Rate: Double,
    val auto: CrescendoAutoAverage,
    val teleop: CrescendoTeleopAverage,
    val stage: CrescendoStageAverage
) : AverageData {
    val melodyRate = rp1Rate
    val ensembleRate = rp2Rate
}
