package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelledRadioButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 4.dp)
    ) {
        RadioButton(selected, onClick)
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LabelledCheckbox(
    label: String,
    selected: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick(!selected) }
            .padding(horizontal = 4.dp)
    ) {
        Checkbox(selected, onCheckedChange = { onClick(it) })
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
