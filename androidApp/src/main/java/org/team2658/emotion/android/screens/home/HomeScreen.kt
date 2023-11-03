package org.team2658.emotion.android.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

//example of using ktor api
@Composable
fun HomeScreen(ktorClient: EmotionClient, nfcViewmodel: NFCViewmodel, primaryViewModel: PrimaryViewModel) {
    var tagData: String? by rememberSaveable {
        mutableStateOf(nfcViewmodel.getNdefPayload())
    }
    var tagStatusText by remember{ mutableStateOf("Scan a tag to log attendance") }
    var showSuccessDialog by remember {mutableStateOf(false)}
    var showFailureDialog by remember { mutableStateOf(false)}
    var failureDialogText by remember {mutableStateOf("")}
    Screen {
        Text(text = "Attendance",
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.size(32.dp))
        if((primaryViewModel.user?.attendance?.size ?: 0) > 0) {
            LinearProgressIndicator(progress = ((primaryViewModel.user?.attendance?.get(0)?.totalHoursLogged?.toFloat()?: 0f) / 36.0f).coerceAtMost(1.0f), modifier = Modifier
                .height(32.dp)
                .fillMaxWidth())
            Text("${primaryViewModel.user?.attendance?.get(0)?.totalHoursLogged} / 36 hours", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text=tagStatusText, style = MaterialTheme.typography.titleLarge)
        if(tagData != null) {
            tagStatusText = "Tag Scanned"
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {
               runBlocking {
                   val user = ktorClient.attendMeeting(primaryViewModel.user, tagData!!, LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli(), failureCallback = { showFailureDialog = true; failureDialogText = it })
                   if (user != null) {
                       tagStatusText = "Scan a tag to log attendance"
                       primaryViewModel.updateUser(user)
                       tagData = null
                       showSuccessDialog = true
                   }else {
                       showFailureDialog = true
                   }
               }}) {
                Text("Log Attendance")
            }
        }
        if(showSuccessDialog) {
            AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showSuccessDialog = false })  {
                Text("Ok")
            }}, title = { Text("Attendance Logged") }, text = { Text("Attendance logged successfully") })
        }
        if(showFailureDialog) {
            AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showFailureDialog = false })  {
                Text("Ok")
            }}, title = { Text("Error") }, text = { Text("Something went wrong logging attendance\n $failureDialogText") })
        }
    }
        else {
            Text("No attendance data found")
        }
    }
}