package org.team2658.emotion.android.screens.settings

import androidx.compose.runtime.Composable
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel


@Composable
fun SettingsScreen(viewModel: PrimaryViewModel) {
    Screen {
        when(viewModel.authState) {
            AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(viewModel::login, viewModel::register)
            AuthState.AWAITING_VERIFICATION-> AwaitingVerificationScreen(viewModel::logout, viewModel.user)
            AuthState.LOGGED_IN -> SettingsLoggedIn(viewModel.user, viewModel::logout)
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