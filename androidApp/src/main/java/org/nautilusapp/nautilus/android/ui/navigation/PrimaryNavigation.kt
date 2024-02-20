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
import org.nautilusapp.nautilus.android.screens.settings.SettingsScreen
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

//    Scaffold(
//        bottomBar = { NavBar(navController, primaryViewModel) },
//        snackbarHost = { SnackbarHost(snack) {
//            Snackbar(snackbarData = it, containerColor = cardColor(), contentColor = MaterialTheme.colorScheme.onSurface)
//        } }
//    ) { padding ->
//        NavHost(
//            navController = navController,
//            startDestination = AppScreens.HOME.name,
//            Modifier.padding(padding)
//        ) {
//            composable(AppScreens.HOME.name) {
//                HomeScreen(nfcViewmodel = nfcViewmodel, primaryViewModel = primaryViewModel, snack = snack)
//            }
//            composable(AppScreens.SETTINGS.name) {
//                SettingsScreen(primaryViewModel, snack = snack)
//            }
//            composable(AppScreens.LEADS.name) {
//                LeadsScreen(viewModel = primaryViewModel, nfc = nfcViewmodel, dataHandler = dataHandler, snack = snack)
//            }
//            composable(AppScreens.SCOUTING.name) {
//                ScoutingScreen(primaryViewModel)
//            }
//        }
//    }

    ScreenScaffold(
        navController = navController,
        accountType = primaryViewModel.user?.accountType,
        perms = primaryViewModel.user?.permissions,
        snack = snack
    ) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.HOME.name,
        ) {
            composable(AppScreens.HOME.name) {
                HomeScreen(
                    primaryViewModel = primaryViewModel,
                    snack = snack,
                    dataHandler = dataHandler,
                    nfcViewmodel = nfcViewmodel
                )
            }
            composable(AppScreens.SETTINGS.name) {
                NestedScaffold(snack = snack, topBar = {
                    TopAppBar(title = {
                        Text("Settings")
                    })
                }) {
                    SettingsScreen(primaryViewModel, snack = snack)
                }
            }
        }
    }
}