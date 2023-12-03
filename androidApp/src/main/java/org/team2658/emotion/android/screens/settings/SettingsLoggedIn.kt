package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.team2658.emotion.android.ui.composables.UserInfoCard
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.userauth.AuthState
import org.team2658.emotion.userauth.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLoggedIn(user: User?, vm: PrimaryViewModel) {
    var syncSuccess: Boolean? by remember { mutableStateOf(null) }
    var statusText: String by remember { mutableStateOf("") }
    var chargedUpQueueLen by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        chargedUpQueueLen = vm.getChargedUpQueueLength()
    }

    if(vm.authState == AuthState.AWAITING_VERIFICATION){
        Text(
            text = "Awaiting Verification",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "Please contact a team lead to verify your account, then log in again.",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.size(32.dp))
        UserInfoCard(User = user)
        Spacer(modifier = Modifier.size(32.dp))
    }
    else {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        UserInfoCard(User = user)

        Spacer(modifier = Modifier.size(32.dp))
    }
    Row {
        Button(onClick = { vm.logout() }) {
            Text(text = "Log Out")
        }
        Spacer(modifier = Modifier.size(16.dp))
        IconButton(onClick = {
            syncSuccess = vm.sync()
            statusText = if(syncSuccess== true) "Sync successful" else "Sync failed"
        }) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    OutlinedButton(onClick = {
        android.app.AlertDialog.Builder(context)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Yes") { _, _ ->
                scope.launch {
                    vm.getClient().deleteMe(vm.user) {success, message ->
                        syncSuccess = success
                        statusText = message
                        if(success) vm.logout()
                    }
                }
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }) {
        Text(text = "DELETE ACCOUNT")
    }

    syncSuccess?.let {
        AlertDialog(onDismissRequest = { }, confirmButton = {
            Button(onClick = { syncSuccess = null; statusText = "" }) {
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