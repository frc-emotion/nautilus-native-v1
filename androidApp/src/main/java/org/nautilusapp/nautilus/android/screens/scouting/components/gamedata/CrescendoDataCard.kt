package org.nautilusapp.nautilus.android.screens.scouting.components.gamedata

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo

@Composable
fun CrescendoDataCard(
    data: Crescendo,
    lookupUser: (String) -> String? = { null }
) {
    with(data) {
        Card(colors = CardDefaults.cardColors(containerColor = cardColor())) {
            Column(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "#$teamNumber: $teamName",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    "Competition: ${data.competition} match #$matchNumber",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Submission ID: $_id, scouted by ${lookupUser(createdBy) ?: createdBy}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Score: $finalScore",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = when {
                        won -> "Won Match"
                        tied -> "Tied Match"
                        else -> "Lost Match"
                    },
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Penalty Points Earned: $penaltyPointsEarned",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Ranking Points Earned: $rankingPoints",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Column(Modifier.padding(start = 8.dp)) {
                    Text(
                        "Melody: ${ranking.melody}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        "Ensemble: ${ranking.ensemble}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.size(4.dp))
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Broke Down: $brokeDown",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Defense Bot: $defensive",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "Auto:",
                    style = MaterialTheme.typography.bodySmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(auto) {
                        Text(
                            "Did Leave: $leave",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Amp: $attempted",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Speaker: $scored",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(Modifier.size(4.dp))
                Text(
                    "Teleop:",
                    style = MaterialTheme.typography.bodySmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(teleop) {
                        Text(
                            "Amp: $ampNotes",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Speaker no amplify: $speakerUnamped",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Speaker w/ amplify: $speakerAmped",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(Modifier.size(4.dp))
                Text(
                    "Endgame:",
                    style = MaterialTheme.typography.bodySmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(stage) {
                        Text(
                            "State: $state",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Harmony: $harmony",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            "Trap: $trap",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Text(
                    "Comments:",
                    style = MaterialTheme.typography.bodySmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    Text(text = comments, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun CrescendoDataCardPreview() {
    val data = Crescendo.exampleData()
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        CrescendoDataCard(data)
    }
}