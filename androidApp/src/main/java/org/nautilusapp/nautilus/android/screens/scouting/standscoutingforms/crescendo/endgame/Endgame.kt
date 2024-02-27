package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.endgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.components.Incrementer
import org.nautilusapp.nautilus.android.ui.composables.LabelledRadioButton
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.MinimalWarning
import org.nautilusapp.nautilus.android.ui.composables.indicators.Show
import org.nautilusapp.nautilus.android.ui.composables.indicators.TooltipSize
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStageState
import org.nautilusapp.nautilus.scouting.tooltips.CrescendoTooltips

@Composable
fun CrescendoEndgameInput(
    state: CrescendoEndgame
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Endgame", style = MaterialTheme.typography.titleLarge)
        CrescendoTooltips.endgameState.Show()
        Spacer(modifier = Modifier.size(8.dp))
        if (!state.isValid) {
            MinimalWarning("Empty Fields")
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Column(modifier = Modifier.padding(start = 4.dp)) {
        CrescendoStageState.entries.forEach {
            LabelledRadioButton(label = it.displayName, selected = state.parkstate == it) {
                state.parkstate = it
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Harmony", style = MaterialTheme.typography.labelLarge)
        CrescendoTooltips.harmonyBonus.Show(TooltipSize.Small)
    }
    Column(Modifier.padding(start = 4.dp)) {
        LabelledRadioButton(label = "None", selected = state.harmony == 0) {
            state.harmony = 0
        }
        LabelledRadioButton(label = "2 Robot", selected = state.harmony == 1) {
            state.harmony = 1
        }
        LabelledRadioButton(label = "3 Robot", selected = state.harmony == 2) {
            state.harmony = 2
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(label = "Trap Notes", value = state.trap, onValueChange = { state.trap = it })
}

val CrescendoStageState.displayName: String
    get() = when (this) {
        CrescendoStageState.NOT_PARKED -> "Not Parked"
        CrescendoStageState.PARKED -> "Parked"
        CrescendoStageState.ONSTAGE -> "Onstage"
        CrescendoStageState.ONSTAGE_SPOTLIT -> "Onstage (Spotlit)"
    }

@Preview
@Composable
fun EndgameInputPreview() {
    val state = remember { CrescendoEndgame() }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            CrescendoEndgameInput(state)
        }
    }
}