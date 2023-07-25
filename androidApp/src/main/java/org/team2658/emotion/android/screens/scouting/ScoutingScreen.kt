package org.team2658.emotion.android.screens.scouting

import androidx.compose.runtime.Composable
import org.team2658.emotion.android.screens.scouting.standscoutingforms.BaseScoutingForm
import org.team2658.emotion.android.ui.composables.Screen

@Composable
fun ScoutingScreen() {
    Screen {
        BaseScoutingForm(
            competitions = listOf(
                "Competition 1",
                "Competition 2",
                "Competition 3",
                "Competition 4"
            ),
            onFormSubmit = { _, callback -> callback() },
            contentInputsOkay = true,
        ) {

        }
    }
}