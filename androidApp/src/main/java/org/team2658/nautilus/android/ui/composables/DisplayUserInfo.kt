package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team2658.nautilus.toCapitalized
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.User

@Composable
fun UserInfoCard(User: User?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        if (User != null) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    text = "Logged in as ${User.firstName} ${User.lastName}",
                    style = MaterialTheme.typography.headlineMedium
                )
                if (User.accountType == AccountType.ADMIN) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Admin Access ✅",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (User.accountType == AccountType.UNVERIFIED) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Not Verified ❌",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Username: ${User.username}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Email: ${User.email}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Subteam: ${User.subteam.name.toCapitalized()}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Permissions: ", style = MaterialTheme.typography.bodyLarge)
                User.permissions.asList().forEach {
                    if (it.value) Text(
                        text = "✓ ${it.key}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    else Text(
                        text = "✗ ${it.key}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        } else {
            Text(text = "No user logged in", color = MaterialTheme.colorScheme.error)
        }
    }
}
