package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.toCapitalized
import org.nautilusapp.nautilus.userauth.AccountType
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.User
import org.nautilusapp.nautilus.userauth.UserPermissions
import org.nautilusapp.nautilus.userauth.isLead

@Composable
fun UserDetailCard(user: User, isInitiallyExpanded: Boolean = false) {
    var isExpanded by remember { mutableStateOf(isInitiallyExpanded) }
    Card(colors = CardDefaults.cardColors(containerColor = cardColor()),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Column {
//                Row {
                val extra = if (user.isLead) mapOf(
                    "star" to InlineTextContent(
                        Placeholder(
                            width = 1.em,
                            height = 1.em,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                        )
                    ) {
                        Box {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Lead",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxSize(0.9f)
                            )
                        }
                    }
                ) else emptyMap()
                val text = buildAnnotatedString {
                    append(user.firstname)
                    append(" ")
                    append(user.lastname)
                    if (user.isLead) {
                        append("\t")
                        appendInlineContent("star")
                    }
                }
                Text(
//                    text = "${user.firstname} ${user.lastname}",
                    text = text,
                    inlineContent = extra,
                    style = MaterialTheme.typography.headlineMedium
                )
//                    if ((user.isLead)) {
//                        Spacer(modifier = Modifier.size(4.dp))
//                        Icon(
//                            Icons.Filled.Star,
//                            contentDescription = "Lead",
//                            tint = MaterialTheme.colorScheme.outline,
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//                    }
//                }

//            Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "@${user.username}",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = user.subteam.displayName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (isExpanded && user is User.Full) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Grade: ${user.grade}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Phone: ${user.phone}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    val rolesStr =
                        if (user.roles.isEmpty()) "None" else user.roles.joinToString(", ")
                    Text(
                        text = "Roles: $rolesStr",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    when (user.accountType) {
                        AccountType.ADMIN, AccountType.SUPERUSER -> {
                            Spacer(modifier = Modifier.size(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Admin Access ",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Icon(
                                    Icons.Filled.Verified,
                                    contentDescription = "Verified",
                                    tint = Color(0xFF0ac93a)
                                )
                            }
                        }

                        AccountType.LEAD -> {
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "Lead",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        AccountType.UNVERIFIED -> {
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "Not Verified ❌",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        else -> {}
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Permissions: ", style = MaterialTheme.typography.bodyLarge)
                    user.permissions.asMap().forEach {
                        Box(Modifier.padding(start = 16.dp)) {
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
                } else if (user is User.Full) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            ". . . more",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            if (user is User.Full) {
                val icon = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore
                Icon(
                    icon,
                    contentDescription = "Expand Card",
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
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

val Subteam?.displayName: String
    get() = when (this) {
        Subteam.NONE, null -> "No Subteam"
        else -> this.name.toCapitalized()
    }