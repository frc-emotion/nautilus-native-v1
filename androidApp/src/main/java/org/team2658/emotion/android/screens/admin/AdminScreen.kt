package org.team2658.emotion.android.screens.admin

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.NumberInput
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.NFC_Viewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.attendance.Meeting
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(viewModel: PrimaryViewModel, client: EmotionClient, nfc: NFC_Viewmodel) {

    Screen {
        Text(text = "Create a Meeting", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        var meetingValue by remember { mutableIntStateOf(2) }
        var meeting: Meeting? by rememberSaveable { mutableStateOf(null) }
        var meetingType by remember { mutableStateOf("meeting")}
        var meetingDescription by remember {mutableStateOf("")}
        val dateState = rememberDatePickerState(initialSelectedDateMillis = LocalDateTime.now().toInstant(
            ZoneOffset.UTC).toEpochMilli(), initialDisplayMode = DisplayMode.Input)
        val startTimeState = rememberTimePickerState(initialHour = LocalDateTime.now().hour)
        val endTimeState = rememberTimePickerState(initialHour = LocalDateTime.now().hour + meetingValue)
        Text("Meeting Date")
        DatePicker(state = dateState)
        Text("Start Time")
        TimeInput(state = startTimeState)
        Text("End Time")
        TimeInput(state = endTimeState)
        Spacer(modifier = Modifier.size(16.dp))
        NumberInput(label = "Meeting Value", value = meetingValue, onValueChange = {meetingValue = it?:0})
        val startTimeMs = (dateState.selectedDateMillis ?: 0) + (startTimeState.hour * 3600000)
        val endTimeMs = (dateState.selectedDateMillis ?: 0) + (endTimeState.hour * 3600000)
        LabelledTextBoxSingleLine(label = "Meeting Type", text = meetingType, onValueChange = {meetingType = it})
        LabelledTextBoxSingleLine(label = "Meeting Description", text = meetingDescription, onValueChange = {meetingDescription = it})
        Button(onClick = {
            runBlocking {
                meeting = client.createMeeting(user = viewModel.user, type = meetingType, description = meetingDescription, startTime = startTimeMs, endTime = endTimeMs, value = meetingValue)
            }
        }) {
            Text("Create Meeting")
        }
        if(meeting != null) {
            Text("Meeting Created", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Meeting id: ${meeting!!._id}")
            Text(text = "Meeting Type: ${meeting!!.type}")
            Text(text = "Meeting Description: ${meeting!!.description}")
            Text(text = "Meeting Value: ${meeting!!.value}")
            val start = LocalDateTime.ofInstant(Instant.ofEpochMilli(meeting!!.startTime), ZoneOffset.UTC)
            val end = LocalDateTime.ofInstant(Instant.ofEpochMilli(meeting!!.endTime), ZoneOffset.UTC)
            Text(text = "Meeting Start Time: $start")
            Text(text = "Meeting End Time: $end")
            val tag = nfc.nfcTag
            Button(onClick = {
                if(tag != null) {
                    try {
                        nfc.writeToTag(meeting!!._id)
                    }catch(e: Exception) {
                        println(e)
                    }
                }
            }, enabled = tag != null) {
                Text(text = if(tag != null) "Write to Tag" else "Scan a tag to write to it")
            }
        }

    }
}