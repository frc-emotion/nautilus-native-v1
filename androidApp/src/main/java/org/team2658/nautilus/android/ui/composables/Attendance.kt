package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.team2658.nautilus.attendance.MeetingLog
import org.team2658.nautilus.attendance.UserAttendance

@Composable
fun UserAttendanceView(userAttendance: Map<String, UserAttendance>) {
    val attendanceKeys = userAttendance.keys.toList()
    var selectedAttendancePeriod by remember { mutableStateOf(attendanceKeys.lastOrNull()?:"none") }

    Text(text = "Attendance",
        style = MaterialTheme.typography.headlineLarge)
//    Spacer(modifier = Modifier.size(16.dp))
    TextDropDown(label = "Time Period:", value = selectedAttendancePeriod, items = attendanceKeys) {
        selectedAttendancePeriod = it
    }
    if(userAttendance.isNotEmpty()) {
        val hoursLogged = userAttendance[selectedAttendancePeriod]?.totalHoursLogged?.toFloat()
            ?: 0f
        val progress = (hoursLogged / 36.0f).coerceAtMost(1.0f)
//        LinearProgressIndicator(progress = progress, modifier = Modifier
//            .height(16.dp)
//            .fillMaxWidth())
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
            Column(Modifier.weight(1f)) {
                Text("${hoursLogged.toInt()} / 36 \nhours", modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                    softWrap = false,
                    )
            }
            CircularProgressIndicator(progress = progress,
                modifier = Modifier.aspectRatio(1f),
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
                strokeWidth = 12.dp,
                strokeCap = StrokeCap.Round
                )
        }
    } else {
        Text("No attendance data found", style = MaterialTheme.typography.titleLarge)
    }


}

//@Preview
@Composable
fun UserAttendanceViewPreview() {
    Screen {
        UserAttendanceView(
            mapOf("2024spring" to
                    UserAttendance(10, emptyList()),
                "2024fall" to
                        UserAttendance(20, emptyList())
            ))
        Spacer(modifier = Modifier.size(256.dp))
        UserAttendanceView(userAttendance = emptyMap())
    }
}

@Composable
fun AttendanceNfcUI(tagData: MeetingLog?, onLogAttendance: (MeetingLog) -> Unit) {
    val text = if(tagData?.meetingId?.isNotBlank() == true) "Tag Scanned" else "Scan a Tag to Log Attendance"
    Button(onClick = {
        tagData?.let { onLogAttendance(it) }
    }, enabled = tagData != null) {
        Text("Log Attendance")
    }
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.tertiary)
}

@Preview
@Composable
fun AttendanceNfcUIPreview() {
    val space = Modifier.size(48.dp)
    var mock by remember { mutableStateOf<MeetingLog?>(null) }
    Screen {
        UserAttendanceView(userAttendance = emptyMap())
        Spacer(space)
        AttendanceNfcUI(mock) {
            mock = if(mock == null) MeetingLog("2024spring", "unknown") else null
        }

        Spacer(modifier = Modifier.size(64.dp))

        UserAttendanceView(userAttendance = mapOf("2024spring" to
                UserAttendance(10, emptyList())))
        Spacer(space)
        AttendanceNfcUI(if(mock == null) MeetingLog("2024spring", "unknown") else null) {
            mock = if(mock != null) null else MeetingLog("2024spring", "unknown")
        }
    }
}