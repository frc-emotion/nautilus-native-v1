package org.nautilusapp.nautilus.android.screens.admin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel

@Composable
fun LeadsScreen(viewModel: MainViewModel, nfc: NFCViewmodel, dataHandler: DataHandler) {
   if(viewModel.user?.permissions?.viewMeetings == true) {
       AttendanceVerificationPage(viewModel, nfc, dataHandler = dataHandler)
   }else {
       Text("No permissions to view attendance")
   }
}