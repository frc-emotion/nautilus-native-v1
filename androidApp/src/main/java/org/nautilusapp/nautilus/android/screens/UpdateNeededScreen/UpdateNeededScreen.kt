package org.nautilusapp.nautilus.android.screens.UpdateNeededScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.ui.composables.BlackScreen

@Composable
fun UpdateNeededScreen(manifestOk: Boolean?, launchIntent: () -> Unit) {
    BlackScreen {
        if(manifestOk == false) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                Text("App Out of Date", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Please update the app to the latest version", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.clickable {
                    launchIntent()
                })
                Spacer(modifier = Modifier.padding(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier.clickable {
                        launchIntent()
                    }) {
                    Row(modifier = Modifier.padding(32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Icon(Icons.Default.SystemUpdate, contentDescription = "Update in Play Store")
                        Text(text = "Update in Play Store",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}