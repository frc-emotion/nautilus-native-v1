package org.nautilusapp.nautilus.android.screens.scouting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.analysis.average
import org.nautilusapp.nautilus.scouting.analysis.models.base.AverageData
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo

@Composable
fun ScoutingDataCard(
    teamName: String,
    teamNumber: Int,
    data: AverageData,
    matchCount: Int = 0,
    totalCount: Int = 0,
    onClick: () -> Unit
) {
    Card(colors = CardDefaults.cardColors(containerColor = cardColor()),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("#$teamNumber $teamName", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Average Score: ${data.score}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    "Ranking: ${data.rpTotal}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Win Rate: ${data.winRate}", style = MaterialTheme.typography.bodySmall)
            }
            Column(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)) {
                Text("Matches: $matchCount", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Total: $totalCount", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
fun SDPrev() {
    val list = (0..50).map { Crescendo.exampleData() }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            list.map {
                ScoutingDataCard(it.teamName, it.teamNumber, listOf(it).average(), 1, 1) {}
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}