package org.nautilusapp.nautilus.android.screens.scouting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun RPInput(
    names: Pair<String, String>,
    values: Pair<Boolean?, Boolean?>,
    onFirstChanged: (Boolean) -> Unit,
    onSecondChanged: (Boolean) -> Unit
) {
    Text(text = "Ranking Points", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.size(8.dp))
    Column {
        YesNoSelector(label = names.first, value = values.first, onFirstChanged)
        Spacer(modifier = Modifier.size(8.dp))
        YesNoSelector(names.second, values.second, onSecondChanged)
    }
}

@Preview
@Composable
fun RPInputPreview() {
    val names = Pair("First", "Second")
    var first: Boolean? by remember { mutableStateOf(null) }
    var second: Boolean? by remember { mutableStateOf(null) }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            RPInput(
                names = names,
                values = Pair(first, second),
                onFirstChanged = { first = it },
                onSecondChanged = { second = it }
            )
        }
    }
}