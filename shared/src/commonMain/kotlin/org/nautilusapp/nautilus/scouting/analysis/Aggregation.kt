package org.nautilusapp.nautilus.scouting.analysis

import org.nautilusapp.nautilus.scouting.scoutingdata.ScoutingData

fun <T : ScoutingData> organize(scouterData: List<T>): Map<String, Map<Int, Map<Int, List<T>>>> {
    return scouterData
        .groupBy { it.competition } //by competition -> { [competition: String]: [list] }
        .mapValues { (k, v) ->
            v.groupBy { it.teamNumber } // { [competition: String] : { [teamNumber: Int] : [list] } }
                .mapValues { (i, j) ->
                    j.groupBy { it.matchNumber }
                    // { [competition: String] : { [teamNumber: Int] : { [matchNumber: Int] : [list] } } }
                }
        }
}