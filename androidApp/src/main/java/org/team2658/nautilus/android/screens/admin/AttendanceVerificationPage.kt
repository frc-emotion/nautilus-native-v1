package org.team2658.nautilus.android.screens.admin

import android.content.DialogInterface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
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
import org.team2658.nautilus.DataHandler
import org.team2658.nautilus.android.ui.composables.DropDown
import org.team2658.nautilus.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.nautilus.android.ui.composables.NumberInput
import org.team2658.nautilus.android.ui.composables.Screen
import org.team2658.nautilus.android.viewmodels.MainViewModel
import org.team2658.nautilus.android.viewmodels.NFCViewmodel
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.attendance.MeetingType
import org.team2658.nautilus.userauth.AccountType
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
    return zdt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AttendanceVerificationPage(viewModel: MainViewModel, nfc: NFCViewmodel, dataHandler: DataHandler) {

    val attendancePeriods = dataHandler.seasons.getAttendancePeriods().values.flatten()

    val scope = rememberCoroutineScope()
    var showCreateMenu by remember { mutableStateOf(false) }

//    var showNFCErrorDialog by remember {mutableStateOf(false)}
    var writeResult by remember {mutableStateOf(NFCWriteResult(false, ""))}

    var showMeetingSuccessDialog by remember {mutableStateOf(false)}
    var meetingCreationStatus by remember {mutableStateOf("")}

    var meetingValue by remember { mutableIntStateOf(2) }
    var meetingType by remember { mutableStateOf("general") }
    var attendancePeriod by remember { mutableStateOf(attendancePeriods.firstOrNull()?:"") }
    var meetingDescription by remember { mutableStateOf("") }

    val context = LocalContext.current

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
//        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input)

    val initialHour = LocalDateTime.now().hour
    val initialMinute = (LocalDateTime.now().minute - (LocalDateTime.now().minute % 15)).coerceAtLeast(0)
    val endingHour = when(initialMinute) {
        in 0..45 -> initialHour + meetingValue
        else -> initialHour + 1 + meetingValue
    }
    val startTimeState = rememberTimePickerState(initialHour = initialHour, initialMinute = initialMinute)
    val endTimeState = rememberTimePickerState(initialHour = (endingHour).coerceAtMost(23), initialMinute = initialMinute)

    var meetings: List<Meeting> by remember { mutableStateOf(dataHandler.attendance.getCurrent(System.currentTimeMillis())) }

    var showPast by remember { mutableStateOf(false) }
    var pastMeetings: List<Meeting> by remember { mutableStateOf(dataHandler.attendance.getOutdated(System.currentTimeMillis())) }
    var archived by remember { mutableStateOf(dataHandler.attendance.getArchived()) }
    var showArchived by remember { mutableStateOf(false)}

    var deletionSuccess by remember { mutableStateOf("")}

    var meetingIdToWrite by remember { mutableStateOf("") }
    val scanningForTag = meetingIdToWrite.isNotBlank()

    val connected by nfc.tagConnectionFlow.collectAsState(initial = false)


    fun loadMeetings() {
        meetings = dataHandler.attendance.getCurrent(System.currentTimeMillis()) {
            meetings = it
        }
        pastMeetings = dataHandler.attendance.getOutdated(System.currentTimeMillis()) {
            pastMeetings = it
        }
        archived = dataHandler.attendance.getArchived {
            archived = it
        }
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
        DropDown(label = "Meeting Type", value = meetingType, items = MeetingType.values().map { it.value } , onValueChange = {meetingType = it} )
        Spacer(modifier = Modifier.size(8.dp))
        DropDown(label = "Attendance Period", value = attendancePeriod, items = attendancePeriods, onValueChange = {
            attendancePeriod = it
        })
        Spacer(modifier = Modifier.size(8.dp))
        LabelledTextBoxSingleLine(
            label = "Meeting Description",
            text = meetingDescription,
            onValueChange = { meetingDescription = it })
        Button(onClick = {
            scope.launch {
                dataHandler.attendance.create(
                    type = meetingType,
                    description = meetingDescription,
                    startTime = startTimeMs,
                    endTime = endTimeMs,
                    value = meetingValue,
                    attendancePeriod = attendancePeriod,
                    onError = {
                        meetingCreationStatus = "Error creating meeting: $it"
                        showMeetingSuccessDialog = true
                    }
                ) {
                    meetingCreationStatus = "Successfully created meeting"
                    showMeetingSuccessDialog = true
                    loadMeetings()
                }
            }
            showCreateMenu = false
        }) {
            Text("Create Meeting")
        }
    }

    @Composable
    fun MeetingItem(meeting: Meeting) {
        Card(Modifier.padding(vertical = 8.dp)) {
            Column (Modifier.padding(16.dp)) {
                Text(
                    text = "Meeting id: ${meeting._id}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(text = "Attendance Period: ${meeting.attendancePeriod ?: "unknown"}")
                Text(text = "Meeting Type: ${meeting.type}")
                if(meeting.description.isNotBlank()) Text(text = "Meeting Description: ${meeting.description}")
                Text(text = "Meeting Value: ${meeting.value}")
                val start = formatFromEpoch(meeting.startTime)
                val end = formatFromEpoch(meeting.endTime)
                Text(text = "Meeting Start Time: $start")
                Text(text = "Meeting End Time: $end")
                Text(text = "Meeting Created By: ${meeting.username ?: meeting.createdBy}")
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    Button(onClick = {
                        meetingIdToWrite = meeting._id
                    }) {
                        Text("Write to Tag")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    if (viewModel.user?.isAdmin == true) {
                        IconButton(onClick = {
                            android.app.AlertDialog.Builder(context)
                                .setTitle("Delete Meeting")
                                .setMessage("Are you sure you want to permanently delete this meeting?")
                                .setPositiveButton("Confirm") { _: DialogInterface, _: Int ->
                                    scope.launch {
                                        dataHandler.attendance.delete(meeting._id).let {
                                            deletionSuccess =
                                                if (it) "Successfully deleted meeting" else "Error deleting meeting"
                                        }
                                        loadMeetings()
                                    }
                                }
                                .setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
                                .show()
                        }) {
                            Icon(Icons.Filled.DeleteForever, contentDescription = "Delete Meeting", tint = MaterialTheme.colorScheme.error)
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(onClick = {dataHandler.attendance.archiveMeeting(meeting._id){loadMeetings()} }){
                            Icon(if(meeting.isArchived == true) Icons.Filled.Unarchive else Icons.Filled.Archive,
                                contentDescription = "Archive Meeting", tint = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }
            }
        }
    }


    Screen(onRefresh = { dataHandler.attendance.sync(); loadMeetings() }) {
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
            if((viewModel.user?.accountType?.value ?: 0) >= AccountType.ADMIN.value) {
                IconButton(onClick = { showPast = !showPast }) {
                    Icon(Icons.Filled.History,
                        contentDescription = "Old",
                        tint = if(showPast) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline
                    )
                }
                IconButton(onClick = { showArchived = !showArchived }) {
                    Icon(Icons.Filled.Archive,
                        contentDescription = "Archived",
                        tint = if(showArchived) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
        if (showCreateMenu) {
            MeetingCreationScreen()
        }
        Spacer(modifier = Modifier.size(16.dp))
        if(meetings.isNotEmpty()){
            meetings.forEach{ MeetingItem(it) }
        }
        else {
            Text(text = "No meetings available", style = MaterialTheme.typography.titleMedium)
        }
        if((viewModel.user?.isAdmin == true) && showPast) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Past Meetings", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.size(8.dp))
            if(pastMeetings.isNotEmpty()){
                pastMeetings.forEach{ MeetingItem(meeting = it) }
            }
            else {
                Text(text = "None Found", style = MaterialTheme.typography.titleMedium)
            }
        }
        if((viewModel.user?.isAdmin == true) && showArchived) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Archived Meetings", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.size(8.dp))
            if(archived.isNotEmpty()){
                archived.forEach{ MeetingItem(meeting = it) }
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
                val success = nfc.writeToTag(meetingIdToWrite, viewModel.user?._id ?: "unknown")
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
                meetingCreationStatus = ""
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = meetingCreationStatus)
        }, text = {
            Text(text = meetingCreationStatus)
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