package org.team2658.emotion.android.screens.settings

import androidx.compose.runtime.Composable
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.userauth.AuthState


@Composable
fun SettingsScreen(viewModel: PrimaryViewModel) {
    Screen {
        when(viewModel.authState) {
            AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(viewModel::login, viewModel::register)
            AuthState.LOGGED_IN, AuthState.AWAITING_VERIFICATION -> SettingsLoggedIn(viewModel.user, viewModel)
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