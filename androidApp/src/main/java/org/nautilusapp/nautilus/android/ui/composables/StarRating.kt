package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun StarRating(stars: Int, fraction: Double, onValueChange: (Double) -> Unit) {
    val value = fraction * stars
    Row {
        (1..stars).forEach {
            val icon = when {
                it <= value -> Icons.Default.Star
                it.toDouble() - 0.5 <= value -> Icons.AutoMirrored.Default.StarHalf
                else -> Icons.Default.StarOutline
            }
            val sub = if (it.toDouble() == value) 0.5 else 0.0
            IconButton(onClick = { onValueChange((it.toDouble() - sub) / stars) }) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewStars() {
    var stars by remember {
        mutableStateOf(1.0)
    }

    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            StarRating(5, stars) { stars = it }
        }
    }
}