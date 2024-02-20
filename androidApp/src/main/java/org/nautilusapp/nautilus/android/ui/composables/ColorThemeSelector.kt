package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.LoadingSpinner
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun ColorThemeSelector(value: ColorTheme, onValueChange: suspend (ColorTheme) -> Unit) {
    var isDebounce by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LoadingSpinner(isDebounce)
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Column(modifier = Modifier.padding(32.dp)) {
            Text(text = "Theme", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(8.dp))
            ColorTheme.entries.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        if(!isDebounce) {
                            scope.launch {
                                isDebounce = true
                                onValueChange(it)
                                isDebounce = false
                            }
                        }
                    }
                ) {
                    RadioButton(selected = (value == it), onClick = {
                        if(!isDebounce) {
                            scope.launch {
                                isDebounce = true
                                onValueChange(it)
                                isDebounce = false
                            }
                        }
                    })
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = it.displayName, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33)
fun ColorThemeSelectorPreview() {
    var selectedTheme by remember { mutableStateOf(ColorTheme.MATERIAL3) }
    MainTheme(preference = selectedTheme, darkTheme = false ) {
        Screen {
            ColorThemeSelector(selectedTheme) { delay(500L); selectedTheme = it }
        }
    }

}