package org.team2658.emotion.android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.screens.admin.AdminScreen
import org.team2658.emotion.android.screens.home.HomeScreen
import org.team2658.emotion.android.screens.scouting.ScoutingScreen
import org.team2658.emotion.android.screens.settings.SettingsScreen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel

@Composable
fun LoggedInNavigator(
    primaryViewModel: PrimaryViewModel,
    scoutingViewModel: StandScoutingViewModel,
    ktorClient: EmotionClient
) {
    val navController = rememberNavController();

    Scaffold(bottomBar = { NavBar(navController, primaryViewModel) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.HOME.name,
            Modifier.padding(padding)
        ) {
            composable(AppScreens.HOME.name) {
                HomeScreen(ktorClient)
            }
            composable(AppScreens.SETTINGS.name) {
                SettingsScreen(primaryViewModel)
            }
            composable(AppScreens.ADMIN.name) {
                AdminScreen(primaryViewModel)
            }
            composable(AppScreens.SCOUTING.name) {
                ScoutingScreen(scoutingViewModel, primaryViewModel)
            }
        }
    }
}