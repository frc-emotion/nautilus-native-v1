package org.nautilusapp.nautilus.android.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.android.ui.composables.LoadingSpinner
import org.nautilusapp.nautilus.android.ui.composables.LoginInput
import org.nautilusapp.nautilus.android.ui.composables.LoginType
import org.nautilusapp.nautilus.android.ui.composables.Screen
import org.nautilusapp.nautilus.android.ui.composables.UserInfoCard
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.userauth.AuthState
import org.nautilusapp.nautilus.userauth.authState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLoggedIn(vm: MainViewModel) {
    val user = vm.user
    var syncSuccess: Boolean? by remember { mutableStateOf(null) }
    var statusText: String by remember { mutableStateOf("") }
    var syncBusy: Boolean by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()


    if(authState(user) == AuthState.AWAITING_VERIFICATION){
        Text(
            text = "Awaiting Verification",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "Please contact a team lead to verify your account, then refresh",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.size(32.dp))
        UserInfoCard(user = user)
        Spacer(modifier = Modifier.size(32.dp))
    }
    else {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        UserInfoCard(user = user)

        Spacer(modifier = Modifier.size(32.dp))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        ShouldSyncIndicator(count = vm.getQueueLength().attendance)
        Spacer(modifier = Modifier.size(8.dp))
        if(syncBusy) {
            Text(text = "Syncing...")
        } else {
            TextButton(onClick = {
                vm.sync { busy, success ->
                    syncSuccess = success; syncBusy = busy
                    statusText = when (success) {
                        true -> "Successfully synced with server"
                        false -> "Failed to connect to server, make sure you are connected to the internet and try again"
                        null -> ""
                    }
                }
            }) {
                Text(text = "Sync")
            }
        }
    }
    Spacer(modifier = Modifier.size(32.dp))
    Button(onClick = { vm.logout() }) {
        Text(text = "Log Out")
    }
    Spacer(modifier = Modifier.size(16.dp))

    Spacer(modifier = Modifier.size(16.dp))
    TextButton(onClick = {
        showDeleteDialog = true
    }) {
        Text(text = "Delete Account", color = MaterialTheme.colorScheme.error)
    }

    LoadingSpinner(syncBusy)

    if(showDeleteDialog){
        Dialog(onDismissRequest = { showDeleteDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Delete Account", style = MaterialTheme.typography.displayMedium)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Are you sure you want to delete your account? This action cannot be undone.")
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Enter your password to confirm.")
                    Spacer(modifier = Modifier.size(4.dp))
                    LoginInput(type = LoginType.CONFIRM_PASSWORD, text = password, onValueChange = {password = it })
                    Spacer(modifier = Modifier.size(8.dp))
                    Row {
                        Button(onClick = {
                            scope.launch {
                                vm.deleteMe(password) {success, message ->
                                    syncSuccess = success
                                    statusText = message ?: "An unknown error occurred."
                                    if(success) vm.logout()
                                }
                            }
                        }) {
                            Text(text = "DELETE ACCOUNT")
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        OutlinedButton(onClick = { showDeleteDialog = false }) {
                            Text(text = "CANCEL")
                        }
                    }
                }
                Spacer(modifier = Modifier.size(32.dp))
            }
        }
    }

    syncSuccess?.let {
        AlertDialog(onDismissRequest = { }, confirmButton = {
            Button(onClick = { syncSuccess = null }) {
                Text(text = "OK")
            }
        }, title = {
                   val text = if(it) "Success" else "Error"
            Text(text = text)
        }, text = {
            Text(statusText)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShouldSyncIndicator(count: Long) {
    var showInfo by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            if(count > 0)
                Icon(Icons.Filled.SyncProblem, contentDescription = "Refresh", tint = Color(0xFFfa6e02))
            else 
                Icon(Icons.Filled.CloudDone, contentDescription = "Synced", tint = Color(0xFF388E3C))
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = if(count > 0) "$count items to sync" else "Up to date")
            if(count > 0) {
                IconButton(onClick = {showInfo = true}) {
                    Icon(Icons.Filled.Info, contentDescription = "Info", tint = Color(0xEE717171))
                }
            }
        }
    }
    if(showInfo) {
        AlertDialog(onDismissRequest = { showInfo = false },
            title = {
                Text(text = "Sync Needed")
            }, text = {
                Text(text = "You have $count items that need to be synced. Please connect to the internet and sync so your attendance and scouting data can be recorded")
            }, confirmButton = {
                Button(onClick = { showInfo = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Composable
@Preview
fun ShouldSyncIndicatorPreview() {
    Screen {
        ShouldSyncIndicator(3)

        Spacer(modifier = Modifier.size(32.dp))

        ShouldSyncIndicator(0)
    }
}