package org.nautilusapp.nautilus.android.ui.composables.indicators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import org.nautilusapp.nautilus.scouting.tooltips.TooltipInfo

@Composable
fun ModalTooltip(text: String, title: String, size: TooltipSize = TooltipSize.Default) {
    var show by remember { mutableStateOf(false) }
    IconButton(onClick = { show = true }, modifier = Modifier.scale(size.scale)) {
        Icon(Icons.Filled.Info, contentDescription = "Info", tint = Color(0xEE717171))
    }

    if (show) {
        AlertDialog(onDismissRequest = { show = false },
            title = {
                Text(text = title)
            }, text = {
                Text(text = text)
            }, confirmButton = {
                Button(onClick = { show = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Composable
fun TooltipInfo.Show(size: TooltipSize = TooltipSize.Default) {
    ModalTooltip(text = description, title = title, size = size)
}

enum class TooltipSize(val scale: Float) {
    Default(1.0f),
    Small(0.75f)
}