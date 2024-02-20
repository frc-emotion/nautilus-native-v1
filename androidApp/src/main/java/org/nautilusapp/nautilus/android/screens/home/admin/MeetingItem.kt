package org.nautilusapp.nautilus.android.screens.home.admin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.attendance.Meeting
import java.time.format.FormatStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MeetingItem(
    meeting: Meeting,
    onWrite: (String) -> Unit,
    selectedHandler: SelectedHandler = EmptySelectedHandler
) {
    var showDetail by remember { mutableStateOf(false) }

    val haptics = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (selectedHandler.isSelecting) showDetail =
                        !showDetail else selectedHandler.onSelected()
                },
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    if (selectedHandler.isSelecting) selectedHandler.onSelected() else showDetail =
                        !showDetail
                }
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (selectedHandler.isSelecting) {
                RadioButton(
                    selected = selectedHandler.selected, onClick = { selectedHandler.onSelected() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = meeting.description.ifBlank { "id: ${meeting._id}" },
                    style = MaterialTheme.typography.titleLarge,
                )
                val start = formatFromEpoch(meeting.startTime, FormatStyle.SHORT)
                val end = formatFromEpoch(meeting.endTime, FormatStyle.SHORT)
                Text(
                    text = "$start to $end"
                )
                Text(
                    text = meeting.attendancePeriod,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                )
                Text(text = "${meeting.value} hours", style = MaterialTheme.typography.labelLarge)
            }
        }

    }

    if (showDetail) {
        Dialog(onDismissRequest = { showDetail = false }) {
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = meeting.description.ifBlank { "id: ${meeting._id}" },
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(text = "Time Period: ${meeting.attendancePeriod}")
                    Text(text = "Type: ${meeting.type}")
                    Text(text = "Value: ${meeting.value}")
                    val start = formatFromEpoch(meeting.startTime)
                    val end = formatFromEpoch(meeting.endTime)
                    Text(text = "Start: $start")
                    Text(text = "End: $end")
                    Text(text = "Created By: ${meeting.username ?: meeting.createdBy}")
                    Text("id: ${meeting._id}")
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = {
                            onWrite(meeting._id)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Write to Tag")
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun MeetingItemPreview() {
    val mtg = Meeting(
        _id = "meowmeow",
        username = "nova",
        attendancePeriod = "2024spring",
        createdBy = ";laksjfa",
        description = "Test meeting",
        isArchived = false,
        startTime = 123L,
        endTime = 12038971208937L,
        type = "test",
        value = 0
    )
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        Screen {
            MeetingItem(
                meeting = mtg,
                onWrite = {}
            )
        }
    }
}