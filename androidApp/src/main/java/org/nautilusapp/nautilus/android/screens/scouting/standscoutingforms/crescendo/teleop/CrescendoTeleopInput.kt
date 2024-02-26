package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.teleop

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.components.Incrementer
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.MinimalWarning
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun CrescendoTeleopInput(
    state: CrescendoTeleopState
) {
    Row {
        Text("Teleop", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(8.dp))
        if (!state.isValid) {
            MinimalWarning("Empty Fields")
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(
        label = "Amp Notes",
        value = state.amp,
        onValueChange = { state.amp = it },
    )
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(
        label = "Speaker Notes",
        value = state.speakerUnamped,
        onValueChange = { state.speakerUnamped = it },
    )
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(
        label = "Speaker Notes (Amplified)",
        value = state.speakerAmped,
        onValueChange = { state.speakerAmped = it },
    )
}

@PreviewFontScale
@Composable
fun CrescendoTeleopPreview() {
    val state = remember {
        CrescendoTeleopState()
    }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            CrescendoTeleopInput(state)
        }
    }
}