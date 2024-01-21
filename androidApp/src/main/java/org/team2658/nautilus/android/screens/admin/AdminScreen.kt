package org.team2658.nautilus.android.screens.admin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.team2658.nautilus.android.viewmodels.NFCViewmodel
import org.team2658.nautilus.android.viewmodels.PrimaryViewModel

@Composable
fun LeadsScreen(viewModel: PrimaryViewModel, nfc: NFCViewmodel) {
   if(viewModel.user?.permissions?.verifyAllAttendance == true || viewModel.user?.permissions?.verifySubteamAttendance == true) {
       AttendanceVerificationPage(viewModel, nfc)
   }else {
       Text("No permissions to view attendance")
   }
}