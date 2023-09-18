package org.team2658.emotion.android.screens.scouting

import androidx.compose.runtime.Composable
import org.team2658.emotion.android.screens.scouting.standscoutingforms.RapidReactForm
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel

@Composable
fun ScoutingScreen(
    scoutingViewModel: StandScoutingViewModel,
    primaryViewModel: PrimaryViewModel
) {
    Screen {
        RapidReactForm(scoutingViewModel = scoutingViewModel, primaryViewModel = primaryViewModel)
    }
}