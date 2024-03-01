package org.nautilusapp.nautilus.android.screens.scouting.dataview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.components.gamedata.CrescendoDataCard
import org.nautilusapp.nautilus.android.screens.scouting.components.gamedata.CrescendoSummaryCard
import org.nautilusapp.nautilus.android.ui.composables.containers.LazyScreen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.analysis.average
import org.nautilusapp.nautilus.scouting.analysis.organize
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo

@Composable
fun CrescendoDetailView(
    teamnumber: Int,
    data: Map<Int, List<Crescendo>>,
    snack: SnackbarHostState,
    getUsername: (String) -> String?,
    onRefresh: suspend () -> DataResult<*>,
    onNavigateBack: () -> Unit
) {
    val summarized = data
        .values
        .map { it.average() }
        .average()
    val teamname = data.values.firstOrNull()?.firstOrNull()?.teamName ?: ""
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, "navigate back")
                }
                Text(text = "Data for Team $teamnumber: $teamname")
            }
            LazyScreen(onRefresh = onRefresh, snack = snack) {
                item {
                    CrescendoSummaryCard(
                        data = summarized,
                        teamnumber = teamnumber,
                        teamname = teamname,
                        matchCount = data.size,
                        subCount = data.values.sumOf { it.size }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }

                data.forEach { (match, subs) ->
                    item {
                        Text(text = "Submissions for Match $match")
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    items(subs, key = { it._id }) {
                        CrescendoDataCard(data = it, lookupUser = getUsername)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CDVP() {
    val data = (0..10000).map { Crescendo.exampleData().copy(comments = "") }
    val o = organize(data).values.toList().first().toList().first()
    val snack = remember { SnackbarHostState() }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        CrescendoDetailView(
            teamnumber = o.first,
            data = o.second,
            snack = snack,
            getUsername = { null },
            onRefresh = { Result.Success(Unit) }) {

        }
    }
}