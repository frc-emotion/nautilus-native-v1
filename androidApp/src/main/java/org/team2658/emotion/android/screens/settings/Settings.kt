package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.android.MainTheme
import org.team2658.emotion.android.viewmodels.SettingsViewModel


@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    Surface(color= MaterialTheme.colorScheme.background, modifier = Modifier
        .fillMaxSize()
        ){
        Scaffold{padding->
            Box(modifier=Modifier.padding(padding)) {
                Column(modifier= Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState(), enabled = true)
                ) {
                    when(viewModel.authState) {
                        AuthState.NOT_LOGGED_IN -> NotLoggedInScreen(viewModel::login, viewModel::register)
                        AuthState.AWAITING_VERIFICATION-> AwaitingVerificationScreen(viewModel::logout, viewModel.user)
                        AuthState.LOGGED_IN -> SettingsLoggedIn(viewModel.user, viewModel::logout)
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun SettingsPreview() {
    val settingsViewModel = SettingsViewModel()
    MainTheme(false) {
        SettingsScreen(settingsViewModel)
    }
}