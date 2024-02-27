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
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.RPInfo
import org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.tooltips.TooltipInfo

@Composable
fun RPInput(
    info: Pair<RPInfo, RPInfo>,
    values: Pair<Boolean?, Boolean?>,
    onFirstChanged: (Boolean) -> Unit,
    onSecondChanged: (Boolean) -> Unit
) {
    Text(text = "Ranking Points", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.size(8.dp))
    Column {
        YesNoSelector(
            label = info.first.name,
            value = values.first,
            info.first.tooltipInfo,
            onFirstChanged
        )
        Spacer(modifier = Modifier.size(8.dp))
        YesNoSelector(
            info.second.name,
            values.second,
            info.second.tooltipInfo,
            onSecondChanged
        )
    }
}

@Preview
@Composable
fun RPInputPreview() {
    val info = RPInfo(name = "Meow", tooltipInfo = TooltipInfo("", ""))
    val info2 = info.copy(name = "Meow2")
    var first: Boolean? by remember { mutableStateOf(null) }
    var second: Boolean? by remember { mutableStateOf(null) }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            RPInput(
                info = Pair(info, info2),
                values = Pair(first, second),
                onFirstChanged = { first = it },
                onSecondChanged = { second = it }
            )
        }
    }
}