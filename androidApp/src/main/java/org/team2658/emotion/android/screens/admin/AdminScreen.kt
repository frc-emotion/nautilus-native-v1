package org.team2658.emotion.android.screens.admin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.runBlocking
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.PrimaryViewModel

@Composable
fun AdminScreen(viewModel: PrimaryViewModel) {
    var text by remember{ mutableStateOf("Loading...") }
    LaunchedEffect(Unit) {
        val response = runBlocking { viewModel.testMeeting() }
        text = response.toString()
    }
    Screen {
        Text(text)
    }
}