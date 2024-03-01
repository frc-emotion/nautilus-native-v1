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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.analysis.average
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoAutoAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoStageAverage
import org.nautilusapp.nautilus.scouting.analysis.models.crescendo.CrescendoTeleopAverage
import org.nautilusapp.nautilus.scouting.analysis.organize
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo

@Composable
fun CrescendoSummaryCard(
    data: CrescendoAverage,
    teamnumber: Int,
    teamname: String,
    matchCount: Int,
    subCount: Int
) {
    with(data.pretty) {
        Card(colors = CardDefaults.cardColors(containerColor = cardColor())) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    "Summary for #$teamnumber: $teamname",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "$subCount submissions over $matchCount matches",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Average Score: $score",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Average penalty earned: $penalty",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Average winrate: $winRate ",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Average tie rate: $tieRate",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Defense bot rate: $defensiveRate",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Breakdown Rate: $breakdownRate",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Average RP Earned: $rpAverage",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Cumulative RP: $rpTotal",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Melody Rate: $melodyRate",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Ensemble rate: $ensembleRate",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    "Auto: ",
                    style = MaterialTheme.typography.titleSmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(auto) {
                        Text(
                            "Leave rate: $leaveRate",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Average amp: $ampNotes",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Average speaker: $speakerNotes",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
                Text(
                    "Teleop: ",
                    style = MaterialTheme.typography.titleSmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(teleop) {
                        Text(
                            "Average amp: $ampNotes",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Average speaker w/o amplify: $speakerUnamped",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Average amplified speaker: $speakerAmped",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
                Text(
                    "Endgame: ",
                    style = MaterialTheme.typography.titleSmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    with(stage) {
                        Text(
                            "Park rate: $parkRate",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Onstage rate: $onstageRate",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Spotlit rate: $spotlitRate",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Harmony average: $harmony",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Trap Average: $trap",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
                Text(
                    "Comments: ",
                    style = MaterialTheme.typography.titleSmall
                )
                Column(Modifier.padding(start = 8.dp)) {
                    comments.filter { it.isNotBlank() }.forEach {
                        Text(text = it, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CSumP() {
    val data = (0..1000).map { Crescendo.exampleData() }
    val o = organize(data).values.toList().first()

    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            o.forEach { (k, v) ->
                val teamname = v.values.first().first().teamName
                val avg = v.mapValues { it.value.average() }.values.toList().average()
                val compcount = v.size
                val subcount = v.values.sumOf { it.size }
                CrescendoSummaryCard(
                    data = avg,
                    teamnumber = k,
                    teamname = teamname,
                    matchCount = compcount,
                    subCount = subcount
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}


val Double.pretty: Double
    get() = String.format("%.3f", this).toDouble()

val CrescendoAverage.pretty: CrescendoAverage
    get() = CrescendoAverage(
        score.pretty,
        penalty.pretty,
        winRate.pretty,
        tieRate.pretty,
        comments,
        defensiveRate.pretty,
        breakdownRate.pretty,
        rpAverage.pretty,
        rpTotal.pretty,
        rp1Rate.pretty,
        rp2Rate.pretty,
        CrescendoAutoAverage(
            auto.leaveRate.pretty,
            auto.ampNotes.pretty,
            auto.speakerNotes.pretty
        ),
        CrescendoTeleopAverage(
            teleop.ampNotes.pretty,
            teleop.speakerUnamped.pretty,
            teleop.speakerAmped.pretty
        ),
        CrescendoStageAverage(
            stage.parkRate.pretty,
            stage.onstageRate.pretty,
            stage.spotlitRate.pretty,
            stage.harmony.pretty,
            stage.trap.pretty
        )
    )