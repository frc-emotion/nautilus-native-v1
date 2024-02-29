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
import org.nautilusapp.nautilus.android.screens.scouting.dataview.CrescendoDataViewScreen
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.CrescendoForm
import org.nautilusapp.nautilus.android.screens.settings.SettingsScreen
import org.nautilusapp.nautilus.android.screens.users.UsersScreen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel
import org.nautilusapp.nautilus.userauth.TokenUser
import org.nautilusapp.nautilus.userauth.UserPermissions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedInNavigator(
    primaryViewModel: MainViewModel,
    dataHandler: DataHandler,
    nfcViewmodel: NFCViewmodel,
    snack: SnackbarHostState
) {
    val navController = rememberNavController()
    val perms = primaryViewModel.user?.permissions

    ScreenScaffold(
        navController = navController,
        user = primaryViewModel.user,
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
                if (primaryViewModel.user.canViewScoutingPage) {

                    when {
                        perms.generalScoutingOnly -> {
                            NestedScaffold(snack = snack, topBar = {
                                TopAppBar(title = {
                                    Text("Crescendo Scouting")
                                })
                            }) {
                                CrescendoForm(dh = dataHandler, snack)
                            }
                        }

                        perms.canViewScoutingDataOnly -> {
                            NestedScaffold(snack = snack, topBar = {
                                TopAppBar(title = {
                                    Text("Crescendo Scouting")
                                })
                            }) {
                                CrescendoDataViewScreen(dh = dataHandler, snack = snack)
                            }
                        }

                        primaryViewModel.user.bothScouting -> {
                            ScoutingNav(dh = dataHandler, snack = snack)
                        }
                    }
                }
            }
        }
    }
}

val TokenUser?.canViewScoutingPage: Boolean
    get() = this?.permissions?.generalScouting == true || this?.permissions?.viewScoutingData == true

val UserPermissions?.canViewScoutingDataOnly: Boolean
    get() = this?.generalScouting != true && this?.viewScoutingData == true

val UserPermissions?.generalScoutingOnly: Boolean
    get() = this?.generalScouting == true && !this.viewScoutingData

val TokenUser?.bothScouting: Boolean
    get() = this?.permissions?.generalScouting == true && this.permissions.viewScoutingData

