package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.ScoutingSubmissionImpl
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto.CrescendoAutoState
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.endgame.CrescendoEndgame
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.teleop.CrescendoTeleopState
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoRankingPoints
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStage
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoSubmission
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoTeleop

class CrescendoVM : ViewModel() {
    val auto = CrescendoAutoState()
    val teleop = CrescendoTeleopState()
    val endgame = CrescendoEndgame()
    var comps: List<String> by mutableStateOf(listOf())
}

fun CrescendoSubmission.Companion.from(
    base: ScoutingSubmissionImpl,
    auto: CrescendoAuto,
    teleop: CrescendoTeleop,
    stage: CrescendoStage
) = CrescendoSubmission(
    auto = auto,
    teleop = teleop,
    stage = stage,
    comments = base.comments,
    ranking = CrescendoRankingPoints(
        melody = base.ranking.rp1,
        ensemble = base.ranking.rp2
    ),
    brokeDown = base.brokeDown,
    competition = base.competition,
    defensive = base.defensive,
    matchNumber = base.matchNumber,
    penaltyPointsEarned = base.penaltyPointsEarned,
    rankingPoints = base.rankingPoints,
    score = base.score,
    teamNumber = base.teamNumber,
    tied = base.tied,
    won = base.won,
    defenseRating = TODO(),
    human = TODO(),
    rating = TODO(),
    coopertition = TODO(),
)