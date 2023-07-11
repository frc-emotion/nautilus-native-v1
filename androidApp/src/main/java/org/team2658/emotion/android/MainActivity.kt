package org.team2658.emotion.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.team2658.emotion.AuthState
import org.team2658.emotion.android.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                SettingsScreen(AuthState.NOT_LOGGED_IN)
                //TODO: auth state controlled by viewmodel
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
