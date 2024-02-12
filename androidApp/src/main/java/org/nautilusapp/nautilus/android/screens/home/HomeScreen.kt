package org.nautilusapp.nautilus.android.screens.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.android.ui.composables.AttendanceNfcUI
import org.nautilusapp.nautilus.android.ui.composables.Screen
import org.nautilusapp.nautilus.android.ui.composables.UserAttendanceView
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel

@Composable
fun HomeScreen(nfcViewmodel: NFCViewmodel, primaryViewModel: MainViewModel) {
//    val tagData = nfcViewmodel.ndefMessages?.get(0)?.records?.get(0)?.payload?.let {
//        String(it, Charset.forName("UTF-8"))
//    }

    val tagData = nfcViewmodel.getNdefData()

    var showSuccessDialog by remember {mutableStateOf(false)}
    var showFailureDialog by remember { mutableStateOf(false)}
    var failureDialogText by remember {mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()
    Screen(onRefresh = primaryViewModel::syncMe) {
        UserAttendanceView(userAttendance = primaryViewModel.user?.attendance ?: mapOf())
        AttendanceNfcUI(tagData = tagData, onLogAttendance = {
            tagData?.let { data ->
                coroutineScope.launch {
                    primaryViewModel.attendMeeting(
                        data,
                        { showFailureDialog = true; failureDialogText = it; nfcViewmodel.setNdef(null) },
                        { showSuccessDialog = true; nfcViewmodel.setNdef(null) }
                    )
                }
            }
        })

        if(showSuccessDialog) {
            AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showSuccessDialog = false })  {
                Text("Ok")
            }}, title = { Text("Attendance Logged") }, text = { Text("Attendance logged successfully") })
        }
        if(showFailureDialog) {
            AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showFailureDialog = false })  {
                Text("Ok")
            }}, title = { Text("Error") }, text = { Text("Something went wrong logging attendance\n $failureDialogText") })
        }
    }
}