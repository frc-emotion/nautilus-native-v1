package org.nautilusapp.nautilus.android.screens.home.admin

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.android.smallCardColor
import org.nautilusapp.nautilus.android.ui.composables.DropDown
import org.nautilusapp.nautilus.android.ui.composables.LabelledTextBoxSingleLine
import org.nautilusapp.nautilus.android.ui.composables.NumberInput
import org.nautilusapp.nautilus.android.ui.composables.indicators.ErrorIndicator
import org.nautilusapp.nautilus.android.ui.composables.indicators.WarningIndicator
import org.nautilusapp.nautilus.attendance.MeetingType
import org.nautilusapp.nautilus.toCapitalized
import org.nautilusapp.nautilus.validation.CreateMeetingArgs
import org.nautilusapp.nautilus.validation.createMeetingValidArgs
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingCreationScreen(
    attendancePeriods: List<String>,
    onCreateMeetings: (CreateMeetingArgs) -> Unit
) {
    var meetingValue by remember { mutableIntStateOf(2) }

    val view = LocalView.current

    val initialHour = LocalDateTime.now().hour
    val initialMinute =
        (LocalDateTime.now().minute - (LocalDateTime.now().minute % 15)).coerceAtLeast(0)
    val endingHourOffset = when (initialMinute) {
        in 0..45 -> meetingValue
        else -> 1 + meetingValue
    }

    val offset = ZoneOffset.systemDefault().rules.getOffset(
        LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC)
    )
    val initMs = LocalDateTime.of(LocalDate.now(), LocalTime.of(initialHour, initialMinute))
        .toInstant(offset).toEpochMilli()
    val defaultEndMs = initMs + endingHourOffset.hours.valueAsMs

    var startTimeMs by remember { mutableLongStateOf(initMs) }
    var endTimeMs by remember { mutableLongStateOf(defaultEndMs) }

    var customEndTimeSet by remember { mutableStateOf(false) }

    if (!customEndTimeSet && endTimeMs != defaultEndMs) {
        endTimeMs = defaultEndMs
    }

    var editStartTime by remember { mutableStateOf(false) }
    var editEndTime by remember { mutableStateOf(false) }

    var meetingType by remember { mutableStateOf("general") }
    var attendancePeriod by remember { mutableStateOf(attendancePeriods.firstOrNull() ?: "") }
    var meetingDescription by remember { mutableStateOf("") }


    @Composable
    fun TimeInputDialog(
        timeType: TimeType
    ) {
        val (edit, init, setEdit, setTime) = when (timeType) {
            TimeType.START -> Quad(
                editStartTime,
                startTimeMs,
                { b: Boolean -> editStartTime = b },
                { time: Long -> startTimeMs = time })

            TimeType.END -> Quad(
                editEndTime,
                endTimeMs,
                { b: Boolean -> editEndTime = b },
                { time: Long -> endTimeMs = time; customEndTimeSet = true })
        }
        val initDateMs =
            if (timeType == TimeType.START) LocalDateTime.now().toInstant(ZoneOffset.UTC)
                .toEpochMilli() else LocalDateTime.now().toInstant(ZoneOffset.UTC)
                .toEpochMilli() + meetingValue * HOURS_TO_SECONDS * 1000
        val zOff = ZoneOffset.systemDefault().rules.getOffset(
            LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC)
        )
        val initDateTime = LocalDateTime.ofEpochSecond(init / 1000, 0, zOff)
        if (edit) {
            val timeState = rememberTimePickerState(
                initialHour = initDateTime.hour,
                initialMinute = initDateTime.minute
            )
            val dateState = rememberDatePickerState(
                initialSelectedDateMillis = initDateMs,
                initialDisplayMode = DisplayMode.Picker
            )
            var showDatePicker by remember { mutableStateOf(false) }
            LaunchedEffect(timeState.hour, timeState.minute) {
                view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            }
            Dialog(onDismissRequest = { setEdit(false) }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor())
                ) {
                    Column(
                        Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                    ) {
                        Text(
                            "${timeType.name.toCapitalized()} Time",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.size(8.dp))
                        Card(
                            shape = RoundedCornerShape(2.dp),
                            colors = CardDefaults.cardColors(containerColor = smallCardColor()),
                            modifier = Modifier.clickable {
                                showDatePicker = true
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = fmtDate(endTimeMs))
                                Spacer(Modifier.size(8.dp))
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                        }
                        Spacer(Modifier.size(8.dp))
                        TimePicker(state = timeState)
                        Row {
                            TextButton(onClick = {
                                setTime(
                                    dateTimeStateToEpochMs(
                                        dateState,
                                        timeState
                                    )
                                ); setEdit(false)
                            }) {
                                Text(text = "Confirm")
                            }
                            TextButton(onClick = { setEdit(false) }) {
                                Text(text = "Cancel", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
            if (showDatePicker) {
                DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Confirm Date")
                    }
                }) {
                    DatePicker(state = dateState, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    val src = remember { MutableInteractionSource() }
    val foc = LocalFocusManager.current
    Box(Modifier.clickable(src, null) {
        foc.clearFocus()
    }) {
        TimeInputDialog(TimeType.START)
        TimeInputDialog(TimeType.END)
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(text = "Create a Meeting", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            LabelledTextBoxSingleLine(
                label = "Meeting Description",
                text = meetingDescription,
                onValueChange = { meetingDescription = it })
            Text("Start Time")
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = smallCardColor()),
                modifier = Modifier.clickable {
                    editStartTime = true
                }
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(text = formatFromEpoch(startTimeMs))
                }
            }
            Spacer(Modifier.size(16.dp))
            Text("End Time")
            Row {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = smallCardColor()),
                    modifier = Modifier.clickable {
                        editEndTime = true
                    }
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(text = formatFromEpoch(endTimeMs))
                    }
                }
                if (customEndTimeSet)
                    IconButton(onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        customEndTimeSet = false
                    }) {
                        Icon(
                            Icons.Default.AutoMode,
                            contentDescription = "auto",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
            }
            Spacer(modifier = Modifier.size(16.dp))
            NumberInput(
                label = "Meeting Value",
                value = meetingValue,
                onValueChange = {
                    meetingValue = it ?: 0
                })
            DropDown(
                label = "Meeting Type",
                value = meetingType,
                items = MeetingType.entries.map { it.value },
                onValueChange = { meetingType = it })
            Spacer(modifier = Modifier.size(8.dp))
            DropDown(
                label = "Attendance Period",
                value = attendancePeriod,
                items = attendancePeriods,
                onValueChange = {
                    attendancePeriod = it
                })
            Spacer(modifier = Modifier.size(8.dp))
            val args =
                CreateMeetingArgs(
                    type = meetingType,
                    description = meetingDescription,
                    startTimeMs = startTimeMs,
                    endTimeMs = endTimeMs,
                    meetingValue = meetingValue,
                    attendancePeriod = attendancePeriod
                )
            val valid = createMeetingValidArgs(args)
            if (!valid.ok) {
                valid.reasons.forEach {
                    Spacer(Modifier.size(8.dp))
                    ErrorIndicator(text = it)
                    Spacer(Modifier.size(8.dp))
                }
            }
            if ((endTimeMs - startTimeMs).ms.valueAsHours < meetingValue) {
                Spacer(Modifier.size(8.dp))
                WarningIndicator(text = "Meeting gives credit for more hours than its duration")
                Spacer(Modifier.size(8.dp))
            }
            if (meetingValue == 0) {
                Spacer(Modifier.size(8.dp))
                WarningIndicator(text = "Meeting grants no hours")
                Spacer(Modifier.size(8.dp))
            }
            if (meetingValue < 0) {
                Spacer(Modifier.size(8.dp))
                WarningIndicator(text = "Meeting will take away hours")
                Spacer(Modifier.size(8.dp))
            }
            Button(
                onClick = {
                    onCreateMeetings(args)
                },
                enabled = createMeetingValidArgs(args).ok
            ) {
                Text("Create Meeting")
            }
            Spacer(modifier = Modifier.size(128.dp))
        }
    }
}


@JvmInline
value class Duration(val valueAsMs: Long) {
    val valueAsSeconds: Long
        get() = valueAsMs / 1000

    val valueAsMinutes: Long
        get() = valueAsSeconds / 60

    val valueAsHours: Long
        get() = valueAsMinutes / 60
}

val Int.hours: Duration
    get() = Duration(this * HOURS_TO_SECONDS * 1000L)

val Int.days: Duration
    get() = Duration(this * 24 * HOURS_TO_SECONDS * 1000L)

val Long.ms: Duration
    get() = Duration(this)

enum class TimeType {
    START, END
}

data class Quad<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)


