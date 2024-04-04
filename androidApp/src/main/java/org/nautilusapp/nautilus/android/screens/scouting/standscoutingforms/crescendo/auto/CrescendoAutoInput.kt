package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.components.Incrementer
import org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.MinimalWarning
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun CrescendoAutoInput(
    state: CrescendoAutoState
) {
    Row {
        Text("Auto", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(8.dp))
        if (!state.isValid) {
            MinimalWarning("Empty Fields")
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(
        label = "Notes Attempted",
        value = state.attempted,
        onValueChange = { state.attempted = it },
    )
    Spacer(modifier = Modifier.size(8.dp))
    Incrementer(
        label = "Notes Scored",
        value = state.scored,
        onValueChange = { state.scored = it },
    )
    Spacer(modifier = Modifier.size(16.dp))
    YesNoSelector(
        label = "Left Alliance Area?",
        value = state.leave,
        setValue = { state.leave = it })
}

@Preview
@Composable
fun CrescendoAutoPreview() {
    val state = remember {
        CrescendoAutoState()
    }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            CrescendoAutoInput(state)
        }
    }
}