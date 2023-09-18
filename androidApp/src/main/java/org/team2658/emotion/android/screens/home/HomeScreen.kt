package org.team2658.emotion.android.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.ui.composables.Screen

//example of using ktor api
@Composable
fun HomeScreen(ktorClient: EmotionClient) {
    Screen {
        var text by remember{ mutableStateOf("Loading...") }
        LaunchedEffect(Unit) {
            val response = runBlocking { ktorClient.getTest() }
            text = response.toString()
        }
        Text(text=text)
    }
}