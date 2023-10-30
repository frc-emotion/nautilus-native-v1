package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.ui.composables.UserInfoCard
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.userauth.User

@Composable
fun SettingsLoggedIn(user: User?, vm: PrimaryViewModel) {
    var syncSuccess: Boolean? by remember { mutableStateOf(null) }
    Text(
        text = "Settings",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.size(32.dp))
    UserInfoCard(User = user)
    Spacer(modifier = Modifier.size(32.dp))
    Button(onClick = { vm.logout() }) {
        Text(text = "Log Out")
    }
    Spacer(modifier = Modifier.size(16.dp))
    OutlinedButton(onClick = { syncSuccess = vm.sync() }) {
        Text(text = "Sync Scouting Data with Server")
    }

    syncSuccess?.let {
        AlertDialog(onDismissRequest = { }, confirmButton = {
            Button(onClick = { syncSuccess = null }) {
                Text(text = "OK")
            }
        }, title = {
                   val text = "Sync ${if (it) "Successful" else "Failed"}"
            Text(text = text)
        }, text = {
            val text = if(it) "Scouting data successfully synced with server" else "Cannot Sync; No internet"
            Text(text)
        })
    }
}