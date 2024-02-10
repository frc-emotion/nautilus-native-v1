package org.team2658.nautilus.android.screens.admin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.team2658.nautilus.DataHandler
import org.team2658.nautilus.Result
import org.team2658.nautilus.android.ui.composables.DropDown
import org.team2658.nautilus.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.nautilus.android.ui.composables.LoadingSpinner
import org.team2658.nautilus.android.ui.composables.NumberInput
import org.team2658.nautilus.android.ui.composables.Screen
import org.team2658.nautilus.android.viewmodels.MainViewModel
import org.team2658.nautilus.android.viewmodels.NFCViewmodel
import org.team2658.nautilus.attendance.Meeting
import org.team2658.nautilus.attendance.MeetingType
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.isAdmin
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

    val attendancePeriods = dataHandler.seasons.getAttendancePeriods()

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

    var isBusy by remember { mutableStateOf(false) }

    var selectedMeetings by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(listOf<String>()) }


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
        DropDown(label = "Meeting Type", value = meetingType, items = MeetingType.entries.map { it.value } , onValueChange = {meetingType = it} )
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
            isBusy = true
            scope.launch {
                val res = dataHandler.attendance.create(
                    type = meetingType,
                    description = meetingDescription,
                    startTime = startTimeMs,
                    endTime = endTimeMs,
                    value = meetingValue,
                    attendancePeriod = attendancePeriod,
                )
                when(res) {
                    is Result.Success -> {
                        meetingCreationStatus = "Successfully created meeting"
                        showMeetingSuccessDialog = true
                        loadMeetings()
                        isBusy = false
                    }
                    is Result.Error -> {
                        meetingCreationStatus = "Error creating meeting: ${res.error}"
                        showMeetingSuccessDialog = true
                        isBusy = false
                    }
                }
            }
            showCreateMenu = false
        }) {
            Text("Create Meeting")
        }
    }



    Box {
        Screen(onRefresh = { dataHandler.attendance.sync(); loadMeetings() }) {
            Row {
                Text(text = "Meetings", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {
                    expanded = if(expanded.isEmpty()) meetings.map { it._id } + pastMeetings.map {it._id} + archived.map { it._id } else listOf()
                } ) {
                    Icon(if(expanded.isEmpty()) Icons.Filled.UnfoldMore else Icons.Filled.UnfoldLess, contentDescription = "Expand/Collapse All")
                }
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
                meetings.forEach{ mtg ->
                    MeetingItem(meeting = mtg,
                        onWrite = {meetingIdToWrite = it},
                        selectedHandler = object: SelectedHandler {
                            override fun onSelected() {
                                selectedMeetings = if(selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                            }
                            override val selected: Boolean = selectedMeetings.contains(mtg._id)
                            override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                            override val shouldExpand: Boolean = expanded.contains(mtg._id)
                        }
                    )
                }
            }
            else {
                Text(text = "No meetings available", style = MaterialTheme.typography.titleMedium)
            }
            if(isAdmin(viewModel.user) && showPast) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Past Meetings", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.size(8.dp))
                if(pastMeetings.isNotEmpty()){
                    pastMeetings.forEach{ mtg ->
                        MeetingItem(meeting = mtg,
                            onWrite = {meetingIdToWrite = it},
                            selectedHandler = object: SelectedHandler {
                                override fun onSelected() {
                                    selectedMeetings = if(selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                                }
                                override val selected: Boolean = selectedMeetings.contains(mtg._id)
                                override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                                override val shouldExpand: Boolean = expanded.contains(mtg._id)
                            }
                        )
                    }
                }
                else {
                    Text(text = "None Found", style = MaterialTheme.typography.titleMedium)
                }
            }
            if(isAdmin(viewModel.user) && showArchived) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Archived Meetings", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.size(8.dp))
                if(archived.isNotEmpty()){
                    archived.forEach{ mtg ->
                        MeetingItem(meeting = mtg,
                            onWrite = {meetingIdToWrite = it},
                            selectedHandler = object: SelectedHandler {
                                override fun onSelected() {
                                    selectedMeetings = if(selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                                }
                                override val selected: Boolean = selectedMeetings.contains(mtg._id)
                                override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                                override val shouldExpand: Boolean = expanded.contains(mtg._id)
                            }
                        )
                    }
                }
                else {
                    Text(text = "None Found", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        if(selectedMeetings.isNotEmpty()) {
            Card(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                ,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(Modifier.padding(2.dp)) {
                    if(isAdmin(viewModel.user)) {
                        IconButton(onClick = {
                            isBusy = true
                            scope.launch {
                                selectedMeetings.forEach {
                                    dataHandler.attendance.delete(it)
                                    selectedMeetings -= it
                                }
                                loadMeetings()
                                isBusy = false
                            }
                        }) {
                            Icon(
                                Icons.Filled.DeleteForever,
                                contentDescription = "Delete",
                                tint = Color(0xFFb83939)
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(onClick = {
                            isBusy = true
                            scope.launch {
                                selectedMeetings.forEach {
                                    dataHandler.attendance.archiveMeeting(it)
                                    selectedMeetings -= it
                                }
                                loadMeetings()
                                isBusy = false
                            }
                        }) {
                            Icon(
                                Icons.Filled.Archive,
                                contentDescription = "Archive",
                                tint = Color(0xFF33a147)
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    IconButton(onClick = { selectedMeetings.forEach {
                        expanded = if(expanded.contains(it)) expanded - it else expanded + it
                    } }) {
                        Icon(if(expanded == selectedMeetings) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown, contentDescription = "Collapse")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(onClick = { selectedMeetings = listOf() }) {
                        Icon(Icons.Filled.Cancel, contentDescription = "Cancel")
                    }
                }
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

    LoadingSpinner(isBusy)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MeetingItem(meeting: Meeting,
                onWrite: (String) -> Unit,
                selectedHandler: SelectedHandler = EmptySelectedHandler) {
    var collapsed by remember { mutableStateOf(true) }

    LaunchedEffect(selectedHandler.shouldExpand){
        collapsed = !selectedHandler.shouldExpand
    }

    val haptics = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (selectedHandler.isSelecting) collapsed =
                        !collapsed else selectedHandler.onSelected()
                },
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    if (selectedHandler.isSelecting) selectedHandler.onSelected() else collapsed =
                        !collapsed
                }
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { collapsed = !collapsed }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    if(collapsed) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if(selectedHandler.isSelecting) {
                RadioButton(
                    selected = selectedHandler.selected, onClick = { selectedHandler.onSelected() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            Column (Modifier.padding(16.dp)) {
                Text(
                    text = "Meeting id: ${meeting._id}",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(text = "Time Period: ${meeting.attendancePeriod ?: "unknown"}")
                if(meeting.description.isNotBlank()) Text(text = "Description: ${meeting.description}")
                Text(text = "Type: ${meeting.type}")
                if(!collapsed) {
                    Text(text = "Value: ${meeting.value}")
                    val start = formatFromEpoch(meeting.startTime)
                    val end = formatFromEpoch(meeting.endTime)
                    Text(text = "Start Time: $start")
                    Text(text = "End Time: $end")
                    Text(text = "Meeting Created By: ${meeting.username ?: meeting.createdBy}")
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(onClick = {
                         onWrite(meeting._id)
                    }) {
                        Text("Write to Tag")
                    }
                }
            }
        }

    }
}



@Preview
@Composable
fun LoadingSpinnerPreview() {
   Screen {
       var selected by remember { mutableStateOf(Pair(false, false)) }

       MeetingItem(meeting = Meeting(
           _id = "test",
           startTime = 696969L,
           endTime = 420420L,
           type = "general",
           description = "test",
           value = 2,
           createdBy = "test",
           attendancePeriod = "test",
           username = "test",
           isArchived = false

       ), onWrite = {},
        selectedHandler = object: SelectedHandler {
               override fun onSelected() { selected = selected.copy(first = !selected.first) }
               override val selected: Boolean = selected.first
               override val isSelecting: Boolean = selected.first || selected.second
               override val shouldExpand: Boolean = true
           }
       )

       MeetingItem(meeting = Meeting(
           _id = "test",
           startTime = 696969L,
           endTime = 420420L,
           type = "general",
           description = "test",
           value = 2,
           createdBy = "test",
           attendancePeriod = "test",
           username = "test",
           isArchived = false

       ), onWrite = {},
           selectedHandler = object: SelectedHandler {
               override fun onSelected() { selected = selected.copy(second = !selected.second) }
               override val selected: Boolean = selected.second
               override val isSelecting: Boolean = selected.first || selected.second
               override val shouldExpand: Boolean = false
           }
       )
   }
}


data class NFCWriteResult(val success: Boolean, val meetingId: String)

interface SelectedHandler {
    fun onSelected()
    val selected: Boolean
    val isSelecting: Boolean
    val shouldExpand: Boolean

}

object EmptySelectedHandler: SelectedHandler {
    override fun onSelected() {}
    override val selected: Boolean = false
    override val isSelecting: Boolean = false
    override val shouldExpand: Boolean = false
}