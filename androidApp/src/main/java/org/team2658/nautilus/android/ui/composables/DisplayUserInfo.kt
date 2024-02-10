package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.team2658.nautilus.toCapitalized
import org.team2658.nautilus.userauth.AccountType
import org.team2658.nautilus.userauth.User
import org.team2658.nautilus.userauth.UserPermissions
import org.team2658.nautilus.userauth.isAdmin

@Composable
fun UserInfoCard(user: User?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        if (user != null) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    text = "Logged in as ${user.firstname} ${user.lastname}",
                    style = MaterialTheme.typography.headlineMedium
                )
                if (isAdmin(user)) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Admin Access ",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(Icons.Filled.Verified, contentDescription = "Verified", tint = Color(0xFF0ac93a))
                    }

                }
                if (user.accountType == AccountType.UNVERIFIED) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Not Verified ❌",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Username: ${user.username}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Subteam: ${user.subteam?.name?.toCapitalized()?: "None"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                if(user is User.Full) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Permissions: ", style = MaterialTheme.typography.bodyLarge)
                    user.permissions.asMap().forEach {
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
            }
        } else {
            Text(text = "No user logged in", color = MaterialTheme.colorScheme.error)
        }
    }
}

fun UserPermissions.asMap(): Map<String, Boolean> {
    return mapOf(
        "Scouting" to generalScouting,
        "Pit Scouting" to pitScouting,
        "View Meetings List" to viewMeetings,
        "View Scouting Data" to viewScoutingData,
        "Make Blog Posts" to blogPosts,
        "Delete Meetings" to deleteMeetings,
        "Make Announcements" to makeAnnouncements,
        "Make Meetings" to makeMeetings
    )
}