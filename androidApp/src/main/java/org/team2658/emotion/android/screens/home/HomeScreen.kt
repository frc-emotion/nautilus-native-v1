package org.team2658.emotion.android.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.NFC_Viewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

//example of using ktor api
@Composable
fun HomeScreen(ktorClient: EmotionClient, nfcViewmodel: NFC_Viewmodel, primaryViewModel: PrimaryViewModel) {
    var tagData by rememberSaveable {
        mutableStateOf(nfcViewmodel.getNdefPayload())
    }
    var text by remember{ mutableStateOf("Scan a tag to log attendance") }
    Screen {
        Text(text = "Attendance", style = MaterialTheme.typography.titleLarge)
        Text(text=text)
        if(tagData != null) {
            text = "Tag Scanned"
            Button(onClick = {
               runBlocking {
                   val user = ktorClient.attendMeeting(primaryViewModel.user, tagData!!, LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                   if (user != null) {
                       text = "Scan a tag to log attendance"
                       primaryViewModel.updateUser(user)
                       tagData = null
                   }

               }}) {
                Text("Log Attendance")
            }
        }
        Text(text = "User attendance: ${primaryViewModel.user?.attendance?.get(0)?.totalHoursLogged} / 36 hours")
    }
}