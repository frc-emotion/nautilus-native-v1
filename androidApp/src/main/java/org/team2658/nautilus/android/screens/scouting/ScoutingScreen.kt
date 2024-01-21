package org.team2658.nautilus.android.screens.scouting

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.team2658.nautilus.android.ui.composables.Screen
import org.team2658.nautilus.android.viewmodels.PrimaryViewModel

@Composable
fun ScoutingScreen(
    primaryViewModel: PrimaryViewModel
) {
    Screen {
//        ChargedUpForm(primaryViewModel = primaryViewModel)
        Text("Scouting functionality is currently disabled", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.error)
    }
}