package org.team2658.emotion.android.screens.scouting.standscoutingforms

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.team2658.emotion.android.ui.composables.DropDown

@Composable
fun BaseScoutingForm(competitions: List<String>) {
    var competition by remember { mutableStateOf(competitions[0]) }
    DropDown(label = "Competition", value = competition) {
        competitions.forEach { comp ->
            DropdownMenuItem(text = { Text(comp) }, onClick = { competition = comp })
            Divider()
        }
    }
}