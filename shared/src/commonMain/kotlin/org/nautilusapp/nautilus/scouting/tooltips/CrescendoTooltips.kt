package org.nautilusapp.nautilus.scouting.tooltips

object CrescendoTooltips {
    val endgameState = TooltipInfo(
        title = "Endgame State",
        description = "Not parked: The robot is outside of their stage zone at the end of the game\n" +
                "Parked: The robot is fully within the bounds of the stage zone (the triangular area on their alliance's side of the field with the chains and traps\n" +
                "Onstage: The robot is hanging on the chain in their stage area and is not touching the ground or another chain\n" +
                "Onstage + Spotlit: The robot qualifies for Onstage AND a human player has scored the note on the corresponding pole for that robot's chain"
    )
    val harmonyBonus = TooltipInfo(
        title = "Harmony Bonus",
        description = "2 Robot: 2 of the alliance's robots are Onstage using the same chain\n" +
                "3 Robot: All 3 of the alliance's robots are Onstage using the same chain\n" +
                "None: The alliance does not have any Onstage robots sharing a chain"
    )

    val melodyRP = TooltipInfo(
        title = "Melody Ranking Point",
        description = "The alliance scored at least 18 Amp & Speaker notes combined, or 15 if they achieved the Coopertition bonus\n" +
                "This is shown on screen at the end of the match"
    )

    val ensembleRP = TooltipInfo(
        title = "Ensemble Ranking Point",
        description = "The alliance earned at least 10 Stage points combined and had at least 2 Onstage robots\n" +
                "This is shown on screen at the end of the match."
    )
}