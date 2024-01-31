package org.team2658.nautilus.android.screens.settings

import androidx.compose.runtime.Composable
import org.team2658.nautilus.android.ui.composables.Screen
import org.team2658.nautilus.android.viewmodels.MainViewModel
import org.team2658.nautilus.userauth.AuthState
import org.team2658.nautilus.userauth.User


@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    Screen(onRefresh = viewModel::coroutineSync) {
        when(User.authState(viewModel.user)) {
            AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(viewModel::login, viewModel::register)
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