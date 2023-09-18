package org.team2658.emotion.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
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

@Composable
fun DropDown(label: String, value: String, children: @Composable () -> Unit) {
    var open by remember { mutableStateOf(false) }
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(16.dp))
    Box() {
        OutlinedButton(onClick = { open = !open }) {
            Text(text = value)
            Icon(Icons.Outlined.ExpandMore, contentDescription = "More")
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            children()
        }
    }
}