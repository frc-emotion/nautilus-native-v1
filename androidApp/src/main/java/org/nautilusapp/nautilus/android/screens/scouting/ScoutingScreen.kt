package org.nautilusapp.nautilus.android.screens.scouting

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.nautilusapp.nautilus.android.ui.composables.Screen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel

@Composable
fun ScoutingScreen(
    primaryViewModel: MainViewModel
) {
    Screen {
//        ChargedUpForm(primaryViewModel = primaryViewModel)
        Text("Scouting functionality is currently disabled", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.error)
    }
}