package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YesNoSelector(
    label: String,
    value: Boolean?,
    setValue: (Boolean) -> Unit
) {
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = value == true, onClick = { setValue(true) })
        Text(text = "Yes", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(8.dp))
        RadioButton(selected = value == false, onClick = { setValue(false) })
        Text(text = "No", style = MaterialTheme.typography.labelLarge)
    }
}