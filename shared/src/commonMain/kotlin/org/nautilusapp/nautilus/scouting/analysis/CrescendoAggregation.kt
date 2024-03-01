package org.nautilusapp.nautilus.scouting.analysis

import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoAutoAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoStageAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoTeleopAverage
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStage
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStageState
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoTeleop
import kotlin.jvm.JvmName

fun List<Crescendo>.average(): CrescendoAverage {
    val score = map { it.finalScore.toDouble() }.average()
    val pen = map { it.penaltyPointsEarned.toDouble() }.average()
    val win = filter { it.won }.size.toDouble() / size.toDouble()
    val tie = filter { it.tied }.size.toDouble() / size.toDouble()
    val comments = map { it.comments }
    val def = filter { it.defensive }.size.toDouble() / size.toDouble()
    val bd = filter { it.brokeDown }.size.toDouble() / size.toDouble()
    val rp1 = filter { it.ranking.rp1 }.size.toDouble() / size.toDouble()
    val rp2 = filter { it.ranking.rp2 }.size.toDouble() / size.toDouble()
    val rp =
        map { it.rankingPoints.toDouble() }.average()
    val rpTotal = sumOf { it.rankingPoints.toDouble() }
    return CrescendoAverage(
        score = score,
        penalty = pen,
        winRate = win,
        tieRate = tie,
        comments = comments,
        defensiveRate = def,
        breakdownRate = bd,
        rpAverage = rp,
        rpTotal = rpTotal,
        rp1Rate = rp1,
        rp2Rate = rp2,
        auto = map { it.auto }.average(),
        teleop = map { it.teleop }.average(),
        stage = map { it.stage }.average()
    )
}

@JvmName("averageCrescendo")
fun List<CrescendoAverage>.average(): CrescendoAverage {
    return CrescendoAverage(
        score = map { it.score }.average(),
        penalty = map { it.penalty }.average(),
        winRate = map { it.winRate }.average(),
        tieRate = map { it.tieRate }.average(),
        comments = map { it.comments }.flatten(),
        defensiveRate = map { it.defensiveRate }.average(),
        breakdownRate = map { it.breakdownRate }.average(),
        rpAverage = map { it.rpAverage }.average(),
        rpTotal = sumOf { it.rpAverage },
        rp1Rate = map { it.rp1Rate }.average(),
        rp2Rate = map { it.rp2Rate }.average(),
        auto = map { it.auto }.average(),
        teleop = map { it.teleop }.average(),
        stage = map { it.stage }.average()
    )
}

private fun List<CrescendoAuto>.average(): CrescendoAutoAverage {
    val leaveRate = filter { it.leave }.size.toDouble() / size.toDouble()
    val amp = map { it.ampNotes.toDouble() }.average()
    val speaker = map { it.speakerNotes.toDouble() }.average()
    return CrescendoAutoAverage(
        leaveRate = leaveRate,
        ampNotes = amp,
        speakerNotes = speaker
    )
}

@JvmName("averageCrescendoAuto")
private fun List<CrescendoAutoAverage>.average(): CrescendoAutoAverage {
    return CrescendoAutoAverage(
        leaveRate = map { it.leaveRate }.average(),
        ampNotes = map { it.ampNotes }.average(),
        speakerNotes = map { it.speakerNotes }.average()
    )
}

private fun List<CrescendoTeleop>.average(): CrescendoTeleopAverage {
    val amp = map { it.ampNotes }.average()
    val su = map { it.speakerUnamped }.average()
    val sa = map { it.speakerAmped }.average()
    return CrescendoTeleopAverage(
        ampNotes = amp,
        speakerAmped = sa,
        speakerUnamped = su
    )
}

@JvmName("averageCrescendoTeleop")
private fun List<CrescendoTeleopAverage>.average(): CrescendoTeleopAverage {
    return CrescendoTeleopAverage(
        ampNotes = map { it.ampNotes }.average(),
        speakerAmped = map { it.speakerAmped }.average(),
        speakerUnamped = map { it.speakerUnamped }.average()
    )
}

private fun List<CrescendoStage>.average(): CrescendoStageAverage {
    val parkRate =
        filter { it.state != CrescendoStageState.NOT_PARKED }.size.toDouble() / size.toDouble()
    val onstageRate =
        filter { it.state == CrescendoStageState.ONSTAGE || it.state == CrescendoStageState.ONSTAGE_SPOTLIT }.size.toDouble() / size.toDouble()
    val spotlitRate =
        filter { it.state == CrescendoStageState.ONSTAGE_SPOTLIT }.size.toDouble() / size.toDouble()
    val harmony = map { it.harmony.toDouble() }.average()
    val trap = map { it.trapNotes.toDouble() }.average()
    return CrescendoStageAverage(
        parkRate = parkRate,
        onstageRate = onstageRate,
        spotlitRate = spotlitRate,
        harmony = harmony,
        trap = trap
    )
}

@JvmName("averageCrescendoStage")
private fun List<CrescendoStageAverage>.average(): CrescendoStageAverage {
    return CrescendoStageAverage(
        parkRate = map { it.parkRate }.average(),
        onstageRate = map { it.onstageRate }.average(),
        spotlitRate = map { it.spotlitRate }.average(),
        harmony = map { it.harmony }.average(),
        trap = map { it.trap }.average()
    )
}