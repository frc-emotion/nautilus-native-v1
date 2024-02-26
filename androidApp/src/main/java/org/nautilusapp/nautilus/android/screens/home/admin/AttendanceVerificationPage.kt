package org.nautilusapp.nautilus.android.screens.home.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.ui.composables.containers.LazyScreen
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.LoadingSpinner
import org.nautilusapp.nautilus.android.ui.navigation.NestedScaffold
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel
import org.nautilusapp.nautilus.attendance.Meeting
import org.nautilusapp.nautilus.userauth.isAdmin
import org.nautilusapp.nautilus.validation.CreateMeetingArgs
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
    val offset = ZoneOffset.systemDefault().rules.getOffset(
        LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC)
    )
    val selectedHoursSeconds = timeState.hour * HOURS_TO_SECONDS
    val selectedMinutesSeconds = timeState.minute * MINUTES_TO_SECONDS
    val selectedTimeSeconds = selectedHoursSeconds + selectedMinutesSeconds
    val adjustedTimeSeconds = selectedTimeSeconds - offset.totalSeconds
    return (dateState.selectedDateMillis ?: 0) + (adjustedTimeSeconds * SECONDS_TO_MS)
}

fun formatFromEpoch(epoch: EpochMS, style: FormatStyle = FormatStyle.MEDIUM): String {
    val zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.systemDefault())
    return zdt.format(DateTimeFormatter.ofLocalizedDateTime(style))
}

fun fmtDate(epoch: EpochMS): String {
    val zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.systemDefault())
        .toLocalDate()
    return zdt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AttendanceVerificationPage(
    viewModel: MainViewModel,
    nfc: NFCViewmodel,
    dataHandler: DataHandler,
    snack: SnackbarHostState,
    onNav: () -> Unit
) {

    val scope = rememberCoroutineScope()
    var showCreateMenu by remember { mutableStateOf(false) }

//    var showNFCErrorDialog by remember {mutableStateOf(false)}
    var writeResult by remember { mutableStateOf(NFCWriteResult(false, "")) }

    var showMeetingSuccessDialog by remember { mutableStateOf(false) }
    var meetingCreationStatus by remember { mutableStateOf("") }

    var meetings: List<Meeting> by remember {
        mutableStateOf(
            dataHandler.attendance.getCurrent(
                System.currentTimeMillis()
            )
        )
    }

    var showPast by remember { mutableStateOf(false) }
    var pastMeetings: List<Meeting> by remember {
        mutableStateOf(
            dataHandler.attendance.getOutdated(
                System.currentTimeMillis()
            )
        )
    }
    var archived by remember { mutableStateOf(dataHandler.attendance.getArchived()) }
    var showArchived by remember { mutableStateOf(false) }

    var deletionSuccess by remember { mutableStateOf("") }

    var meetingIdToWrite by remember { mutableStateOf("") }
    val scanningForTag = meetingIdToWrite.isNotBlank()

    val connected by nfc.tagConnectionFlow.collectAsState(initial = false)

    var isBusy by remember { mutableStateOf(false) }

    var selectedMeetings by remember { mutableStateOf(listOf<String>()) }

    fun loadMeetings() {
        meetings = dataHandler.attendance.getCurrent(System.currentTimeMillis()) {
            meetings = it
        }
        if (showPast)
            pastMeetings = dataHandler.attendance.getOutdated(System.currentTimeMillis()) {
                pastMeetings = it
            }
        if (showArchived)
            archived = dataHandler.attendance.getArchived {
                archived = it
            }
    }

    LaunchedEffect(showPast, showArchived) {
        loadMeetings()
    }


    val attendancePeriods = dataHandler.seasons.getAttendancePeriods()
    val archiveDisplay = if (showArchived && (viewModel.user.isAdmin)) archived else listOf()
    val pastDisplay = if (showPast && viewModel.user.isAdmin) pastMeetings else listOf()

    val localConf = LocalConfiguration.current

    fun createMeeting(args: CreateMeetingArgs) {
        isBusy = true
        scope.launch {
            val res = dataHandler.attendance.create(
                type = args.type,
                description = args.description,
                startTime = args.startTimeMs,
                endTime = args.endTimeMs,
                value = args.meetingValue,
                attendancePeriod = args.attendancePeriod,
            )
            when (res) {
                is Result.Success -> {
                    meetingCreationStatus = "Successfully created meeting"
                    showMeetingSuccessDialog = true
                    loadMeetings()
                    isBusy = false
                }

                is Result.Error -> {
                    meetingCreationStatus = "Error creating meeting: ${res.error.message}"
                    showMeetingSuccessDialog = true
                    isBusy = false
                }
            }
        }
        showCreateMenu = false
    }

    val emptyList =
        meetings.isEmpty() && (pastMeetings.isEmpty() || !showPast) && (archived.isEmpty() || !showArchived)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var showViewOptionsMenu by remember {
        mutableStateOf(false)
    }

    var showDeleteConfirm by remember { mutableStateOf(false) }

    @Composable
    fun actionButtons() {
        Column(horizontalAlignment = Alignment.End) {
            FloatingActionButton(
                onClick = {
                    showDeleteConfirm = true
                },
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    Icons.Filled.DeleteForever,
                    contentDescription = "Delete",
                )
            }
            val allNotArchived = selectedMeetings.all { s -> archived.find { it._id == s } == null }
            val allArchived = selectedMeetings.all { s -> archived.find { it._id == s } != null }
            if (allNotArchived || allArchived) {
                Spacer(modifier = Modifier.size(18.dp))
                LargeFloatingActionButton(
                    onClick = {
                        isBusy = true
                        val len = selectedMeetings.size
                        scope.launch {
                            selectedMeetings.forEach {
                                dataHandler.attendance.archiveMeeting(it)
                                selectedMeetings -= it
                            }
                            loadMeetings()
                            isBusy = false
                            snack.showSnackbar(
                                "$len meetings ${
                                    if (allArchived) "unarchived" else "archived"
                                }"
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        if (allArchived) Icons.Default.Unarchive else Icons.Default.Archive,
                        contentDescription = "Archive",
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }
            }
        }
    }

    NestedScaffold(
        snack = snack,
        floatingActionButton = {
            if (selectedMeetings.isNotEmpty())
                actionButtons()
        },
        topBar = {
            TopAppBar(
                title = { Text("Meetings") },
                navigationIcon = {
                    IconButton(onClick = {
                        onNav()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "back")
                    }
                },
                actions = {
                    if (viewModel.user?.permissions?.makeMeetings == true) {
                        ElevatedAssistChip(
                            onClick = { showCreateMenu = !showCreateMenu },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            label = {
                                Text("New")
                            }
                        )
                    }
                    if ((viewModel.user.isAdmin)) {
                        Box {
                            IconButton(onClick = { showViewOptionsMenu = true }) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "View options",
                                )
                            }
                            DropdownMenu(
                                expanded = showViewOptionsMenu,
                                onDismissRequest = { showViewOptionsMenu = false }) {
                                DropdownMenuItem(onClick = { showPast = !showPast }, text = {
                                    Row {
                                        if (showPast)
                                            Icon(
                                                Icons.Filled.Check,
                                                contentDescription = null
                                            )
                                        Spacer(Modifier.size(8.dp))
                                        Text("Past Meetings")
                                    }
                                })
                                DropdownMenuItem(
                                    onClick = { showArchived = !showArchived },
                                    text = {
                                        Row {
                                            if (showArchived)
                                                Icon(
                                                    Icons.Filled.Check,
                                                    contentDescription = null
                                                )
                                            Spacer(Modifier.size(8.dp))
                                            Text("Archived Meetings")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }) {
        Box {
            if (showCreateMenu && viewModel.user?.permissions?.makeMeetings == true) {
                ModalBottomSheet(
                    onDismissRequest = { showCreateMenu = false },
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    sheetState = sheetState
                ) {
                    MeetingCreationScreen(
                        onCreateMeetings = { createMeeting(it) },
                        attendancePeriods = attendancePeriods
                    )
                }
            }
            LazyScreen(
                onRefresh = { dataHandler.attendance.sync().download.also { loadMeetings() } },
                snack = snack,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                if (meetings.isEmpty()) {
                    item("No current available") {
                        if (emptyList) Box {
                            Text(
                                text = "No meetings available",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.fillParentMaxHeight())
                        }
                        else {
                            Text(
                                text = "No current meetings available",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                items(meetings.size, { meetings[it]._id }) { i ->
                    val mtg = meetings[i]
                    MeetingItem(meeting = mtg,
                        onWrite = { meetingIdToWrite = it },
                        selectedHandler = object : SelectedHandler {
                            override fun onSelected() {
                                selectedMeetings =
                                    if (selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                            }

                            override val selected: Boolean = selectedMeetings.contains(mtg._id)
                            override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                        }
                    )
                }
                item("Past meetings") {
                    if (showPast && (viewModel.user.isAdmin)) {
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(text = "Past Meetings", style = MaterialTheme.typography.headlineLarge)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                items(pastDisplay.size, key = { pastDisplay[it]._id }) {
                    if (showPast && (viewModel.user.isAdmin)) {
                        val mtg = pastDisplay[it]
                        MeetingItem(meeting = mtg,
                            onWrite = { meetingIdToWrite = it },
                            selectedHandler = object : SelectedHandler {
                                override fun onSelected() {
                                    selectedMeetings =
                                        if (selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                                }

                                override val selected: Boolean = selectedMeetings.contains(mtg._id)
                                override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                            }
                        )
                    }
                }
                item("No past found") {
                    if (pastMeetings.isEmpty() && showPast && (viewModel.user.isAdmin)) Text(
                        text = "None Found",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item("Archived") {
                    if ((viewModel.user.isAdmin) && showArchived) {
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            text = "Archived Meetings",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                items((archiveDisplay.size), { archiveDisplay[it]._id }) {
                    if ((viewModel.user.isAdmin) && showArchived) {
                        val mtg = archiveDisplay[it]
                        MeetingItem(meeting = mtg,
                            onWrite = { meetingIdToWrite = it },
                            selectedHandler = object : SelectedHandler {
                                override fun onSelected() {
                                    selectedMeetings =
                                        if (selected) selectedMeetings - mtg._id else selectedMeetings + mtg._id
                                }

                                override val selected: Boolean = selectedMeetings.contains(mtg._id)
                                override val isSelecting: Boolean = selectedMeetings.isNotEmpty()
                            }
                        )
                    }
                }
                item("no archived") {
                    if ((viewModel.user.isAdmin) && showArchived && archived.isEmpty()) {
                        Text(text = "None Found", style = MaterialTheme.typography.titleMedium)
                    }
                }
                item("space") {
                    if (!emptyList)
                        Spacer(
                            Modifier
                                .height((localConf.screenHeightDp * 0.4).dp)
                                .fillParentMaxWidth()
                        )
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(onClick = {
                    isBusy = true
                    val len = selectedMeetings.size
                    scope.launch {
                        selectedMeetings.forEach {
                            dataHandler.attendance.delete(it)
                            selectedMeetings -= it
                        }
                        loadMeetings()
                        isBusy = false
                        snack.showSnackbar("$len meetings deleted")
                    }
                    showDeleteConfirm = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancel")
                }
            },
            title = {
                Text("Delete ${selectedMeetings.size} meetings?")
            },
            text = {
                Text("This cannot be undone, and members will lose hours for all deleted meetings")
            }
        )
    }

    if (writeResult.meetingId.isNotBlank()) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                writeResult = NFCWriteResult(false, "")
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = if (writeResult.success) "Successfully wrote ${writeResult.meetingId}" else "Error")
        }, text = {
            Text(text = if (writeResult.success) "Successfully wrote ${writeResult.meetingId} to NFC Tag" else "Error writing to tag, please make sure the tag is in range and not damaged")
        })

    }

    if (scanningForTag) {
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
        if (connected) {
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

    if (showMeetingSuccessDialog) {
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

    if (deletionSuccess.isNotEmpty()) {
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
            selectedHandler = object : SelectedHandler {
                override fun onSelected() {
                    selected = selected.copy(first = !selected.first)
                }

                override val selected: Boolean = selected.first
                override val isSelecting: Boolean = selected.first || selected.second
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
            selectedHandler = object : SelectedHandler {
                override fun onSelected() {
                    selected = selected.copy(second = !selected.second)
                }

                override val selected: Boolean = selected.second
                override val isSelecting: Boolean = selected.first || selected.second
            }
        )
    }
}


data class NFCWriteResult(val success: Boolean, val meetingId: String)

interface SelectedHandler {
    fun onSelected()
    val selected: Boolean
    val isSelecting: Boolean
}

object EmptySelectedHandler : SelectedHandler {
    override fun onSelected() {}
    override val selected: Boolean = false
    override val isSelecting: Boolean = false
}