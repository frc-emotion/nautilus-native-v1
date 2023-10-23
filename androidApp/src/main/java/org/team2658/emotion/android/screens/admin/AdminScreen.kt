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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import org.team2658.emotion.android.viewmodels.NFC_Viewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.attendance.Meeting
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun LeadsScreen(viewModel: PrimaryViewModel, client: EmotionClient, nfc: NFC_Viewmodel) {
   if(viewModel.user?.permissions?.verifyAllAttendance == true || viewModel.user?.permissions?.verifySubteamAttendance == true) {
       AttendanceVerificationPage(viewModel, client, nfc)
   }
}