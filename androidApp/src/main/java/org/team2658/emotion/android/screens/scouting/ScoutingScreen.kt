package org.team2658.emotion.android.screens.scouting

import androidx.compose.runtime.Composable
import org.team2658.emotion.android.screens.scouting.standscoutingforms.RapidReactForm
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.SettingsViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel

@Composable
fun ScoutingScreen(
    scoutingViewModel: StandScoutingViewModel,
    settingsViewModel: SettingsViewModel
) {
    Screen {
        RapidReactForm(scoutingViewModel = scoutingViewModel, settingsViewModel = settingsViewModel)
    }
}