package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.ui.composables.UserInfoCard
import org.team2658.emotion.userauth.User

@Composable
fun SettingsLoggedIn(user: User?, onLogout: ()->Unit) {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.size(32.dp))
    UserInfoCard(User = user)
    Spacer(modifier = Modifier.size(32.dp))
    Button(onClick = { onLogout() }) {
        Text(text = "Log Out")
    }
}