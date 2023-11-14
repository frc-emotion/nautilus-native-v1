package org.team2658.emotion.android.screens.admin

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.NumberInput
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.attendance.Meeting
import java.io.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


const val HOURS_TO_MINUTES = 60
const val MINUTES_TO_SECONDS = 60
const val SECONDS_TO_MS = 1000
const val HOURS_TO_SECONDS = HOURS_TO_MINUTES * MINUTES_TO_SECONDS
const val MINUTES_TO_MS = MINUTES_TO_SECONDS * SECONDS_TO_MS
const val HOURS_TO_MS = HOURS_TO_MINUTES * MINUTES_TO_MS

typealias EpochMS = Long

@OptIn(ExperimentalMaterial3Api::class)
fun dateTimeStateToEpochMs(dateState: DatePickerState, timeState: TimePickerState): EpochMS {
    val offset = ZoneOffset.systemDefault().rules.getOffset(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC))
    val selectedHoursSeconds = timeState.hour * HOURS_TO_SECONDS
    val selectedMinutesSeconds = timeState.minute * MINUTES_TO_SECONDS
    val selectedTimeSeconds = selectedHoursSeconds + selectedMinutesSeconds
    val adjustedTimeSeconds = selectedTimeSeconds - offset.totalSeconds
    return (dateState.selectedDateMillis?:0) + (adjustedTimeSeconds * SECONDS_TO_MS)
}

fun formatFromEpoch(epoch: EpochMS): String {
    val zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.systemDefault())
    return zdt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AttendanceVerificationPage(viewModel: PrimaryViewModel, client: EmotionClient, nfc: NFCViewmodel) {

    var showCreateMenu by remember { mutableStateOf(false) }

    var showNFCErrorDialog by remember {mutableStateOf(false)}
    var showMeetingErrorDialog by remember {mutableStateOf(false)}
    var meetingErrorText by remember{mutableStateOf("")}
    var showSuccessDialog by remember {mutableStateOf(false)}
    var showMeetingSuccessDialog by remember {mutableStateOf(false)}

    var meetingValue by remember { mutableIntStateOf(2) }
    val meeting: Meeting? = viewModel.meeting
    var meetingType by remember { mutableStateOf("meeting") }
    var meetingDescription by remember { mutableStateOf("") }

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
        initialDisplayMode = DisplayMode.Input)

    val startTimeState = rememberTimePickerState(initialHour = LocalDateTime.now().hour)
    val endTimeState = rememberTimePickerState(initialHour = (LocalDateTime.now().hour + meetingValue).coerceAtMost(23))

    Screen {
        Text(text = "Create a Meeting or Activate a Tag", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.size(16.dp))
        if (meeting != null) {
            Text("Previously Created Meeting", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Meeting id: ${meeting._id}")
            Text(text = "Meeting Type: ${meeting.type}")
            Text(text = "Meeting Description: ${meeting.description}")
            Text(text = "Meeting Value: ${meeting.value}")
            val start = formatFromEpoch(meeting.startTime)
            val end = formatFromEpoch(meeting.endTime)
            Text(text = "Meeting Start Time: $start")
            Text(text = "Meeting End Time: $end")
            val connected by nfc.tagConnectionFlow.collectAsState(initial = false)
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = {
                if (connected) {
                    try {
                        showSuccessDialog = nfc.writeToTag(meeting._id)
                    }catch(e: IOException) {
                        showNFCErrorDialog = true
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }, enabled = connected) {
                Text(text = if (connected) "Write to Tag" else "Scan a tag to write to it")
            }
            Spacer(modifier = Modifier.size(32.dp))
        }

        Button(onClick = { showCreateMenu = !showCreateMenu }) {
            Text(text = if (showCreateMenu) "Close" else "Create a Meeting")
        }

        if (showCreateMenu) {
            Text(text = "Create a Meeting", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            Text("Meeting Date")
            DatePicker(state = dateState)
            Text("Start Time")
            TimeInput(state = startTimeState)
            Text("End Time")
            TimeInput(state = endTimeState)
            Spacer(modifier = Modifier.size(16.dp))
            NumberInput(
                label = "Meeting Value",
                value = meetingValue,
                onValueChange = { meetingValue = it ?: 0 })
            val startTimeMs = dateTimeStateToEpochMs(dateState, startTimeState)
            val endTimeMs = dateTimeStateToEpochMs(dateState, endTimeState)
            LabelledTextBoxSingleLine(
                label = "Meeting Type",
                text = meetingType,
                onValueChange = { meetingType = it })
            LabelledTextBoxSingleLine(
                label = "Meeting Description",
                text = meetingDescription,
                onValueChange = { meetingDescription = it })
            Button(onClick = {
                runBlocking {
                    val mtg = client.createMeeting(
                        user = viewModel.user,
                        type = meetingType.trim().lowercase(),
                        description = meetingDescription,
                        startTime = startTimeMs,
                        endTime = endTimeMs,
                        value = meetingValue,
                        errorCallback = {
                            meetingErrorText = it
                            showMeetingErrorDialog = true
                        }
                    )
                    if (mtg != null) showMeetingSuccessDialog = true
                    viewModel.updateMeeting(
                        mtg
                    )
                }
            }) {
                Text("Create Meeting")
            }

        }
    }

    if(showNFCErrorDialog) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                showNFCErrorDialog = false
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "NFC Tag Error")
        }, text = {
            Text(text = "Something went wrong accessing the NFC tag. Please make sure the tag is in range and not damaged.")
        })
    }

    if(showMeetingErrorDialog) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                showMeetingErrorDialog = false
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Error")
        }, text = {
            Text(text = "Something went wrong creating the meeting\n $meetingErrorText")
        })
    }

    if(showSuccessDialog) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                showSuccessDialog = false
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Successfully wrote to tag")
        }, text = {
            Text(text = "Meeting ${meeting?._id} was written to tag successfully")
        })
    }

    if(showMeetingSuccessDialog) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                showMeetingSuccessDialog = false
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Successfully created meeting")
        }, text = {
            Text(text = "Meeting ${meeting?._id} was created successfully")
        })
    }
}