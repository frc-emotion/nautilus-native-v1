package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.screens.admin.LeadsScreen
import org.nautilusapp.nautilus.android.screens.home.HomeScreen
import org.nautilusapp.nautilus.android.screens.scouting.ScoutingScreen
import org.nautilusapp.nautilus.android.screens.settings.SettingsScreen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel

@Composable
fun LoggedInNavigator(
    primaryViewModel: MainViewModel,
    dataHandler: DataHandler,
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
                HomeScreen(nfcViewmodel = nfcViewmodel, primaryViewModel = primaryViewModel)
            }
            composable(AppScreens.SETTINGS.name) {
                SettingsScreen(primaryViewModel)
            }
            composable(AppScreens.LEADS.name) {
                LeadsScreen(viewModel = primaryViewModel, nfc = nfcViewmodel, dataHandler = dataHandler )
            }
            composable(AppScreens.SCOUTING.name) {
                ScoutingScreen(primaryViewModel)
            }
        }
    }
}