package org.team2658.emotion.android.screens.admin

import androidx.compose.material3.Button
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
    var text2 by remember{ mutableStateOf("Loading...") }
    LaunchedEffect(Unit) {
        val response = runBlocking { viewModel.testMeeting() }
        text = response.toString()
    }
    Screen {
        Text(text)
        Button(onClick = {
            runBlocking {
                val updated = viewModel.getClient().attendMeeting(viewModel.user, "6508ce2a56b67b0fe1add79d", 1693889668040L)
                text2 = updated.toString()
            }
        }) {
            Text("Attend Meeting")
        }
        Text(text2)
    }
}