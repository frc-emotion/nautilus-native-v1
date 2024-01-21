package org.team2658.nautilus.android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.team2658.nautilus.android.screens.admin.LeadsScreen
import org.team2658.nautilus.android.screens.home.HomeScreen
import org.team2658.nautilus.android.screens.scouting.ScoutingScreen
import org.team2658.nautilus.android.screens.settings.SettingsScreen
import org.team2658.nautilus.android.viewmodels.NFCViewmodel
import org.team2658.nautilus.android.viewmodels.PrimaryViewModel
import org.team2658.network.NetworkClient

@Composable
fun LoggedInNavigator(
    primaryViewModel: PrimaryViewModel,
    ktorClient: NetworkClient,
    nfcViewmodel: NFCViewmodel
) {
    val navController = rememberNavController();

    Scaffold(bottomBar = { NavBar(navController, primaryViewModel) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.HOME.name,
            Modifier.padding(padding)
        ) {
            composable(AppScreens.HOME.name) {
                HomeScreen(ktorClient = ktorClient, nfcViewmodel = nfcViewmodel, primaryViewModel = primaryViewModel)
            }
            composable(AppScreens.SETTINGS.name) {
                SettingsScreen(primaryViewModel)
            }
            composable(AppScreens.LEADS.name) {
                LeadsScreen(viewModel = primaryViewModel, nfc = nfcViewmodel )
            }
            composable(AppScreens.SCOUTING.name) {
                ScoutingScreen(primaryViewModel)
            }
        }
    }
}