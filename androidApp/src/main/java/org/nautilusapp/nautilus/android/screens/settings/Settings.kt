package org.nautilusapp.nautilus.android.screens.settings

import androidx.compose.runtime.Composable
import org.nautilusapp.nautilus.android.screens.settings.loggedout.NotLoggedInScreen
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.userauth.AuthState
import org.nautilusapp.nautilus.userauth.authState


@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    Screen(onRefresh = viewModel::coroutineSync) {
        when(authState(viewModel.user)) {
            AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(onLogin = viewModel::login,
                onRegister = viewModel::register,
                organization = viewModel.organization,
                orgs = viewModel.organizations,
                setOrganization = viewModel::setOrg)
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