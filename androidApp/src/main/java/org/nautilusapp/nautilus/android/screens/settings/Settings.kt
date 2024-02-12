package org.nautilusapp.nautilus.android.screens.settings

import androidx.compose.runtime.Composable
import org.nautilusapp.nautilus.android.screens.settings.loggedout.NotLoggedInScreen
import org.nautilusapp.nautilus.android.ui.composables.Screen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.userauth.AuthState
import org.nautilusapp.nautilus.userauth.authState


@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    Screen(onRefresh = viewModel::coroutineSync) {
        when(authState(viewModel.user)) {
            AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(viewModel::login, viewModel::register, viewModel.getDataHandler().routeBase)
            AuthState.LOGGED_IN, AuthState.AWAITING_VERIFICATION -> SettingsLoggedIn(viewModel)
        }
    }
}


//@Preview
//@Composable
//fun SettingsPreview() {
//    val settingsViewModel = SettingsViewModel()
//    MainTheme(false) {
//        SettingsScreen(settingsViewModel)
//    }
//}