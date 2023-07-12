package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.composables.UserInfoCard
import org.team2658.emotion.userauth.User

@Composable
fun AwaitingVerificationScreen(
    onLogout: () -> Unit,
    user: User?
) {
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
    Button(onClick = { onLogout() }) {
        Text(text = "Log Out")
    }
}