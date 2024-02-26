package org.nautilusapp.nautilus.android.screens.scouting.components

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun Incrementer(
    label: String,
    value: Int?,
    onValueChange: (Int?) -> Unit,
    range: IntRange = 0..100,
) {
    var showError by remember { mutableStateOf(false) }
    val foc = LocalFocusManager.current
    val intSource = remember { MutableInteractionSource() }
    val view = LocalView.current
    fun feedback() {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        view.playSoundEffect(SoundEffectConstants.CLICK)
    }
    Column(modifier = Modifier.clickable(indication = null, interactionSource = intSource) {
        foc.clearFocus()
    }) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            TextField(
                value = (value?.toString() ?: ""),
                onValueChange = {
                    if (it.isNotBlank() && it.toIntOrNull() in range) {
                        onValueChange(it.toInt())
                    } else if (it.isBlank()) onValueChange(null)
                },
                modifier = Modifier
                    .onFocusChanged {
                        if (!showError && it.isFocused) showError = true
                    }
                    .weight(0.75f),
                placeholder = {
                    Text(
                        text = "...",
                        textAlign = TextAlign.Start,
                        softWrap = false,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                isError = showError && (value == null),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(
                Modifier
                    .fillMaxSize()
                    .weight(1.0f)
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    modifier = Modifier
//                        .weight(1.0f)
                        .fillMaxHeight()
                        .aspectRatio(1.0f)
                        .clickable {
                            if (((value ?: 0) - 1) in range) onValueChange((value ?: 0) - 1)
                            feedback()
                        },
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Card(
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .fillMaxHeight()
                        .clickable {
                            if (((value ?: 0) + 1) in range) onValueChange((value ?: 0) + 1)
                            feedback()
                        },
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        if (value == null) {
            Text(
                text = "* Required",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                color = if (showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@PreviewFontScale
@Composable
fun IncrementerPreview() {
    var value: Int? by remember { mutableStateOf(null) }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            Incrementer(label = "Test", value = value, onValueChange = { value = it })
        }
    }
}