package org.team2658.emotion.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.team2658.emotion.android.screens.settings.SettingsScreen
import org.team2658.emotion.android.ui.navigation.LoggedInNavigator
import org.team2658.emotion.android.viewmodels.SettingsViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel
import org.team2658.emotion.userauth.AuthState

class MainActivity : ComponentActivity() {
    private val settingsViewModel by viewModels<SettingsViewModel>()
    private val scoutingViewModel by viewModels<StandScoutingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                if (settingsViewModel.authState == AuthState.LOGGED_IN) {
                    LoggedInNavigator(settingsViewModel, scoutingViewModel)
                } else {
                    Scaffold { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            SettingsScreen(settingsViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MainTheme {
        GreetingView("Hello, Android!")
    }
}
