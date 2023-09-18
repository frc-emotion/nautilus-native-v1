package org.team2658.emotion.android

import android.content.SharedPreferences
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.screens.settings.SettingsScreen
import org.team2658.emotion.android.ui.navigation.LoggedInNavigator
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel
import org.team2658.emotion.userauth.AuthState

class MainActivity : ComponentActivity() {
    private val scoutingViewModel by viewModels<StandScoutingViewModel>()
    private val ktorClient = EmotionClient()
//    private val sharedPrefs = getPreferences(MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPrefs: SharedPreferences = LocalContext.current.getSharedPreferences("org.team2658.emotion.android", MODE_PRIVATE)
            val primaryViewModel = viewModel<PrimaryViewModel>(
                factory = object: ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                    ):T {
                        return PrimaryViewModel(ktorClient, sharedPrefs) as T
                    }
                }
            )
            MainTheme {
                if (primaryViewModel.authState == AuthState.LOGGED_IN) {
                    LoggedInNavigator(primaryViewModel, scoutingViewModel, ktorClient)
                } else {
                    Scaffold { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            SettingsScreen(primaryViewModel)
                        }
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ktorClient.close()
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
