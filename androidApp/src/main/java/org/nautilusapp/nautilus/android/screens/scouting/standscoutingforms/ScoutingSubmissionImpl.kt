package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms

import org.nautilusapp.nautilus.scouting.scoutingdata.RP
import org.nautilusapp.nautilus.scouting.scoutingdata.ScoutingSubmission

data class ScoutingSubmissionImpl(
    override val competition: String,
    override val teamNumber: Int,
    override val matchNumber: Int,
    override val score: Int,
    override val penaltyPointsEarned: Int,
    override val won: Boolean,
    override val tied: Boolean,
    override val comments: String,
    override val defensive: Boolean,
    override val brokeDown: Boolean,
    override val rankingPoints: Int,
    override val ranking: RPImpl,
) : ScoutingSubmission

data class RPImpl(
    override val rp1: Boolean,
    override val rp2: Boolean,
) : RP