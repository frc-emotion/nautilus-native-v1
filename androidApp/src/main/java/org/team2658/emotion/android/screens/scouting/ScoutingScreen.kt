package org.team2658.emotion.android.screens.scouting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.team2658.emotion.android.screens.scouting.standscoutingforms.ChargedUpForm
import org.team2658.emotion.android.screens.scouting.standscoutingforms.RapidReactForm
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.scouting.scoutingdata.ChargedUp

@Composable
fun ScoutingScreen(
    primaryViewModel: PrimaryViewModel
) {
    Screen {
        ChargedUpForm(primaryViewModel = primaryViewModel)
//        Text("Scouting functionality is currently not available")
    }
}