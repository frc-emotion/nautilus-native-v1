package org.team2658.emotion.android.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.MainTheme
import org.team2658.emotion.toCapitalized
import org.team2658.emotion.userauth.AccessLevel
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User
import org.team2658.emotion.userauth.UserPermissions

@Composable
fun UserInfoCard(User: User?, modifier: Modifier = Modifier.fillMaxWidth()) {
    Card(modifier = modifier) {
        if(User != null) {
            Column(modifier = Modifier.padding(32.dp)){
                Text(text = "Logged in as ${User.firstName} ${User.lastName}",
                    style = MaterialTheme.typography.headlineMedium)
                if(User.accessLevel == AccessLevel.ADMIN) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Admin Access ✅",
                        style = MaterialTheme.typography.titleLarge,
                        color= MaterialTheme.colorScheme.primary
                    )
                }
                if(User.accessLevel==AccessLevel.NONE) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Not Verified ❌",
                        style = MaterialTheme.typography.headlineMedium,
                        color=MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Username: ${User.username}",
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Email: ${User.email}",
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Subteam: ${User.subteam.name.toCapitalized()}",
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text="Permissions: ", style = MaterialTheme.typography.bodyLarge)
                if(User.permissions.submitScoutingData) {
                    Text(text = "✓ Submit scouting data", style = MaterialTheme.typography.bodyLarge)
                }
                if(User.permissions.inPitScouting) {
                    Text(text = "✓ In-pit scouting", style = MaterialTheme.typography.bodyLarge)
                }
                if(User.permissions.viewScoutingData) {
                    Text(text = "✓ View scouting data", style = MaterialTheme.typography.bodyLarge)
                }
                if(!User.permissions.submitScoutingData && !User.permissions.inPitScouting && !User.permissions.viewScoutingData) {
                    Text(text = "None", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
                }
            }
        } else {
            Text(text = "No user logged in", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun UserInfoCardPreview() {
    val exampleUser = User(
        "exampleUser",
        "1lkjasldkjfl;awsjkl;sdfj",
        "Example",
        "User",
        "example@example.com",
        AccessLevel.BASE,
        Subteam.SOFTWARE,
        UserPermissions(
            submitScoutingData = true,
            viewScoutingData = true,
            inPitScouting = true,
        )
    )
    MainTheme(true) {
            UserInfoCard(exampleUser)
    }
}