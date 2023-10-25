package org.team2658.emotion.android.screens.admin

import androidx.compose.runtime.Composable
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel

@Composable
fun LeadsScreen(viewModel: PrimaryViewModel, client: EmotionClient, nfc: NFCViewmodel) {
   if(viewModel.user?.permissions?.verifyAllAttendance == true || viewModel.user?.permissions?.verifySubteamAttendance == true) {
       AttendanceVerificationPage(viewModel, client, nfc)
   }
}