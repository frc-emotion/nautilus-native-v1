package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.screens.home.HomeScreen
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.CrescendoForm
import org.nautilusapp.nautilus.android.screens.settings.SettingsScreen
import org.nautilusapp.nautilus.android.screens.users.UsersScreen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedInNavigator(
    primaryViewModel: MainViewModel,
    dataHandler: DataHandler,
    nfcViewmodel: NFCViewmodel,
    snack: SnackbarHostState
) {
    val navController = rememberNavController()

    ScreenScaffold(
        navController = navController,
        perms = primaryViewModel.user?.permissions,
        snack = snack
    ) {
        NavHost(
            navController = navController,
            startDestination = AppScreen.HOME.name,
        ) {
            composable(AppScreen.HOME.name) {
                HomeScreen(
                    primaryViewModel = primaryViewModel,
                    snack = snack,
                    dataHandler = dataHandler,
                    nfcViewmodel = nfcViewmodel
                )
            }
            composable(AppScreen.SETTINGS.name) {
                NestedScaffold(snack = snack, topBar = {
                    TopAppBar(title = {
                        Text("Settings")
                    })
                }) {
                    SettingsScreen(primaryViewModel, snack = snack)
                }
            }
            composable(AppScreen.USERS.name) {
                UsersScreen(dataHandler, snack)
            }
            composable(AppScreen.SCOUTING.name) {
                if (primaryViewModel.user?.permissions?.generalScouting == true)
                    NestedScaffold(snack = snack, topBar = {
                        TopAppBar(title = {
                            Text("Crescendo Scouting")
                        })
                    }) {
                        CrescendoForm(dh = dataHandler, snack)
                    }
            }
        }
    }
}