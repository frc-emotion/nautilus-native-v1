package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun Screen(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = interactionSource) { focusManager.clearFocus() },
    ) {

        Column(
            modifier = Modifier
                .padding(32.dp)
                .verticalScroll(rememberScrollState(), enabled = true)
        ) {
            content()
        }
    }
}

