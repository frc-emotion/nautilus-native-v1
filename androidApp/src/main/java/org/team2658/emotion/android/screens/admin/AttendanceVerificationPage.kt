package org.team2658.emotion.android.screens.admin

import android.content.DialogInterface
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.NumberInput
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.attendance.Meeting
import org.team2658.emotion.userauth.AccountType
import java.time.Instant
import java.time.LocalDateTime
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
fun AttendanceVerificationPage(viewModel: PrimaryViewModel, nfc: NFCViewmodel) {

    val scope = rememberCoroutineScope()
    var showCreateMenu by remember { mutableStateOf(false) }

//    var showNFCErrorDialog by remember {mutableStateOf(false)}
    var writeResult by remember {mutableStateOf(NFCWriteResult(false, ""))}

    var creationSuccessId by remember {mutableStateOf("")}
    var showMeetingSuccessDialog by remember {mutableStateOf(false)}

    var meetingValue by remember { mutableIntStateOf(2) }
    var meetingType by remember { mutableStateOf("meeting") }
    var meetingDescription by remember { mutableStateOf("") }

    val context = LocalContext.current

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
        initialDisplayMode = DisplayMode.Input)

    val initialHour = LocalDateTime.now().hour
    val initialMinute = (LocalDateTime.now().minute - (LocalDateTime.now().minute % 15)).coerceAtLeast(0)
    val endingHour = when(initialMinute) {
        in 0..45 -> initialHour + meetingValue
        else -> initialHour + 1 + meetingValue
    }
    val startTimeState = rememberTimePickerState(initialHour = initialHour, initialMinute = initialMinute)
    val endTimeState = rememberTimePickerState(initialHour = (endingHour).coerceAtMost(23), initialMinute = initialMinute)

    var meetings: List<Pair<Meeting, String?>> by remember { mutableStateOf(listOf()) }

    var showPast by remember { mutableStateOf(false) }
    var pastMeetings: List<Pair<Meeting, String?>> by remember { mutableStateOf(listOf()) }

    var deletionSuccess by remember { mutableStateOf("")}

    var meetingIdToWrite by remember { mutableStateOf("") }
    val scanningForTag = meetingIdToWrite.isNotBlank()

    val connected by nfc.tagConnectionFlow.collectAsState(initial = false)


    suspend fun loadMeetings() {
        viewModel.syncMeetings()
        meetings = viewModel.getMeetings()
        pastMeetings = viewModel.getOutdatedMeetings()
    }

    LaunchedEffect(Unit) {
        loadMeetings()
    }

    @Composable
    fun MeetingCreationScreen() {
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
            scope.launch {
                viewModel.createMeeting(
                    type = meetingType,
                    description = meetingDescription,
                    startTime = startTimeMs,
                    endTime = endTimeMs,
                    value = meetingValue,
                ) {
                    showMeetingSuccessDialog = true
                    creationSuccessId = it._id
                    scope.launch {
                        loadMeetings()
                    }
                }
            }
        }) {
            Text("Create Meeting")
        }
    }

    @Composable
    fun MeetingItem(meeting: Meeting, username: String?) {
        Text(text = "Meeting id: ${meeting._id}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Meeting Type: ${meeting.type}")
        Text(text = "Meeting Description: ${meeting.description}")
        Text(text = "Meeting Value: ${meeting.value}")
        val start = formatFromEpoch(meeting.startTime)
        val end = formatFromEpoch(meeting.endTime)
        Text(text = "Meeting Start Time: $start")
        Text(text = "Meeting End Time: $end")
        Text(text = "Meeting Created By: $username")
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Button(onClick = {
//                if (connected) {
//                    try {
//                        showSuccessDialog = nfc.writeToTag(meeting._id)
//                        creationSuccessId = meeting._id
//                    }catch(e: IOException) {
//                        showNFCErrorDialog = true
//                    } catch (e: Exception) {
//                        println(e)
//                    }
//                }
                meetingIdToWrite = meeting._id
            }) {
                Text("Write to Tag")
            }
            Spacer(modifier = Modifier.size(8.dp))
            if(viewModel.user?.isAdmin == true) {
                IconButton(onClick = {
                    android.app.AlertDialog.Builder(context)
                        .setTitle("Delete Meeting")
                        .setMessage("Are you sure you want to permanently delete this meeting?")
                        .setPositiveButton("Confirm") { _: DialogInterface, _: Int ->
                            scope.launch {
                                viewModel.deleteMeeting(meeting._id) { deletionSuccess = it }
                                meetings = viewModel.getMeetings()
                            }
                        }
                        .setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
                        .show()
                }) {
                    Icon(Icons.Filled.DeleteForever, contentDescription = "Delete Meeting")
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Divider()
        Spacer(modifier = Modifier.size(16.dp))
    }

    Screen {
        Row {
            Text(text = "Meetings", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.size(4.dp))
            IconButton(
                onClick = { showCreateMenu = !showCreateMenu },
            ) {
                if (!showCreateMenu) {
                    Icon(Icons.Filled.PostAdd, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
                } else {
                    Icon(Icons.Filled.Cancel, contentDescription = "Cancel", tint = MaterialTheme.colorScheme.error)
                }
            }
            IconButton(onClick = {
                scope.launch {
                    loadMeetings()
                }
            }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Refresh", tint = MaterialTheme.colorScheme.secondary)
            }

            if((viewModel.user?.accountType?.value ?: 0) >= AccountType.ADMIN.value) {
                IconButton(onClick = { showPast = !showPast }) {
                    Icon(Icons.Filled.CalendarMonth,
                        contentDescription = "Old",
                        tint = if(!showPast) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline
                    )
                }
            }

        }
        if (showCreateMenu) {
            MeetingCreationScreen()
        }
        Spacer(modifier = Modifier.size(16.dp))
        if(meetings.isNotEmpty()){
            meetings.forEach{ (meeting, username) -> MeetingItem(meeting, username ) }
        }
        else {
            Text(text = "No meetings available", style = MaterialTheme.typography.titleMedium)
        }
        if(viewModel.user?.isAdmin == true && showPast) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Past Meetings", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.size(8.dp))
            if(pastMeetings.isNotEmpty()){
                pastMeetings.forEach{ (meeting, username) -> MeetingItem(meeting, username ) }
            }
            else {
                Text(text = "None Found", style = MaterialTheme.typography.titleMedium)
            }
        }
    }

    if(writeResult.meetingId.isNotBlank()) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                writeResult = NFCWriteResult(false, "")
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = if(writeResult.success) "Successfully wrote ${writeResult.meetingId}" else "Error")
        }, text = {
            Text(text = if(writeResult.success) "Successfully wrote ${writeResult.meetingId} to NFC Tag" else "Error writing to tag, please make sure the tag is in range and not damaged" )
        })

    }

    if(scanningForTag) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                meetingIdToWrite = ""
            }) {
                Text(text = "Cancel")
            }
        }, title = {
            Text(text = "Scan a Tag")
        }, text = {
            Text(text = "Tap a tag to the back of your phone to write meeting $meetingIdToWrite to it. Make sure NFC is enabled on your phone")
        })
        if(connected){
            writeResult = try {
                val success = nfc.writeToTag(meetingIdToWrite)
                NFCWriteResult(success, meetingIdToWrite)
            } catch (e: Exception) {
                println(e)
                NFCWriteResult(false, meetingIdToWrite)
            } finally {
                meetingIdToWrite = ""
            }
        }
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
            Text(text = "Meeting $creationSuccessId was created successfully")
        })
    }

    if(deletionSuccess.isNotEmpty()) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                deletionSuccess = ""
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = deletionSuccess)
        }, text = {
            Text(text = deletionSuccess)
        })
    }
}

data class NFCWriteResult(val success: Boolean, val meetingId: String)