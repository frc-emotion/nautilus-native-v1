package org.nautilusapp.nautilus.android.screens.scouting.dataview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.screens.scouting.components.ScoutingDataCard
import org.nautilusapp.nautilus.android.ui.composables.TextDropDown
import org.nautilusapp.nautilus.android.ui.composables.containers.LazyScreen
import org.nautilusapp.nautilus.scouting.analysis.average
import org.nautilusapp.nautilus.scouting.analysis.organize
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo
import org.nautilusapp.nautilus.toCapitalized

@Composable
fun ScoutingDataList(
    data: List<Crescendo>,
    comp: String,
    snack: SnackbarHostState? = null,
    onRefresh: suspend () -> DataResult<*>,
    onItemClicked: (Pair<Int, Map<Int, List<Crescendo>>>) -> Unit
) {
    val organized: Map<Int, Map<Int, List<Crescendo>>> = organize(data)[comp] ?: emptyMap()
//    val sorted = organized
//        .toList()
//        .sortedByDescending { (k, v) ->
//            v
//                .mapValues { (_, v) -> v.average() }
//                .values
//                .toList()
//                .average()
//                .rpTotal
//        }
    var strat by rememberSaveable { mutableStateOf(SortStrategy.SCORE) }

    val sorted = sortData(organized, strat)


    LazyScreen(
        onRefresh = onRefresh,
        snack = snack,
        contentPadding = PaddingValues(8.dp)
    ) {
        item(key = "header") {
            Column {
                TextDropDown(
                    label = "Sort",
                    value = strat,
                    items = SortStrategy.entries,
                    onValueChange = { strat = it }) {
                    it.name.toCapitalized()
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
        items(sorted, key = { it.first }) { (teamNumber, data) ->
            val average = data.values.map { it.average() }.average()
            ScoutingDataCard(
                teamName = data.values.firstOrNull()?.firstOrNull()?.teamName ?: "",
                teamNumber = teamNumber,
                data = average,
                matchCount = data.values.size,
                totalCount = data.values.sumOf { it.size }
            ) {
                onItemClicked(Pair(teamNumber, data))
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

private fun sortData(
    data: Map<Int, Map<Int, List<Crescendo>>>,
    sortBy: SortStrategy
): List<Pair<Int, Map<Int, List<Crescendo>>>> {
    return data
        .toList()
        .sortedByDescending { (k, v) ->
            val collated = v
                .mapValues { (_, v) -> v.average() }
                .values
                .toList()
                .average()
            when (sortBy) {
                SortStrategy.RP -> collated.rpTotal
                SortStrategy.AUTO -> collated.auto.ampNotes + collated.auto.speakerNotes
                SortStrategy.TELEOP -> collated.teleop.speakerAmped + collated.teleop.speakerUnamped + collated.teleop.ampNotes
                SortStrategy.SPEAKER -> collated.teleop.speakerAmped + collated.teleop.speakerUnamped
                SortStrategy.AMP -> collated.teleop.ampNotes
                SortStrategy.SCORE -> collated.score
                SortStrategy.BREAKDOWN -> collated.breakdownRate
                SortStrategy.DEFENSIVE -> collated.defensiveRate
            }
        }
}

private enum class SortStrategy {
    RP,
    AUTO,
    TELEOP,
    SPEAKER,
    AMP,
    SCORE,
    BREAKDOWN,
    DEFENSIVE
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoutingDataListScreen(
    dh: DataHandler,
    snack: SnackbarHostState,
    onItemClicked: (Pair<Int, Map<Int, List<Crescendo>>>) -> Unit
) {
    val comps = dh.seasons.getComps(2024)
    var comp by rememberSaveable { mutableStateOf(comps.lastOrNull()) }
    val initData = dh.crescendo.getAll()
    var data by remember { mutableStateOf(initData) }

    Column {
        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            TextDropDown(
                label = "Competition",
                value = comp ?: "",
                items = comps,
                onValueChange = { comp = it })
        }
        ScoutingDataList(
            data = data,
            comp = comp ?: "",
            snack = snack,
            onRefresh = {
                dh.crescendo.sync().download.also {
                    if (it is Result.Success) data = it.data
                }
            },
            onItemClicked = onItemClicked
        )
    }
}