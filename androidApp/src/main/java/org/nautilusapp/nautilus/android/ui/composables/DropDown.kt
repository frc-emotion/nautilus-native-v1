package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun DropDown(label: String, value: String, children: @Composable () -> Unit) {
    var open by remember { mutableStateOf(false) }
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(8.dp))

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { open = !open }
    ) {
        Box(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if(value.isBlank()) {
                Text(text = label, color = MaterialTheme.colorScheme.outline)
            } else {
                Text(text = value)
            }
            val icon = if(open) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore
            val tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
            Icon(icon, contentDescription = "More",
                tint = tint,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            children()
        }
    }
}

@Composable
fun TextDropDown(label: String, value: String, children: @Composable () -> Unit) {
    var open by remember { mutableStateOf(false) }
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(4.dp))
        Box {
            TextButton(onClick = { open = !open }) {
                if(value.isBlank()) {
                    Text(text = label, color = MaterialTheme.colorScheme.outline)
                } else {
                    Text(text = value)
                }
                val icon = if(open) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore
                val tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
                Icon(icon, contentDescription = "More",
                    tint = tint,
                )
            }
            DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                children()
            }
        }
    }
}

@Composable
fun TextDropDown(label: String, value: String, items: List<String>, onValueChange: (String) -> Unit ) =
    TextDropDown(label, value, items, onValueChange) { it }


@Composable
fun <T> TextDropDown(label: String, value: T, items: List<T>, onValueChange: (T) -> Unit, getStr: (T) -> String) {
    var open by remember { mutableStateOf(false) }
    val str = getStr(value)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(4.dp))
        Box {
            TextButton(onClick = { open = !open }, enabled = items.isNotEmpty()) {
                if(str.isBlank()) {
                    Text(text = label, color = MaterialTheme.colorScheme.outline)
                } else {
                    Text(text = str)
                }
                val icon = if(open) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore
                val tint = if(str.isBlank() || items.isEmpty()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
                Icon(icon, contentDescription = "More",
                    tint = tint,
                )
            }
            DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                items.forEach {
                    DropdownMenuItem(onClick = { onValueChange(it); open = false }, text = {
                        Text(text = getStr(it))
                    })
                }
            }

        }
    }
}

@Composable
fun DropDown(label: String, value: String, items: List<String>, onValueChange: (String) -> Unit) =
    DropDown (label, value, items, onValueChange) { it }


@Composable
fun <T> DropDown(label: String, value: T, items: List<T>, onValueChange: (T) -> Unit, getStr: (T) -> String) {
    var open by remember { mutableStateOf(false) }
    val str = getStr(value)
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(8.dp))
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { open = !open }
    ) {
        Box(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if(str.isBlank()) {
                Text(text = label, color = MaterialTheme.colorScheme.outline)
            } else {
                Text(text = str)
            }
            val icon = if(open) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore
            val tint = if(str.isBlank() || items.isEmpty()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
            Icon(icon, contentDescription = "More",
                tint = tint,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            items.forEach {
                DropdownMenuItem(onClick = { onValueChange(it); open = false }, text = {
                    Text(text = getStr(it))
                })
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33)
fun DropDownPreview() {
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        Screen {
            var selected by remember { mutableStateOf("2024spring")}
            DropDown(label = "Time Period", value = selected) {
                DropdownMenuItem(onClick = { selected = "2024spring" }, text = {
                    Text(text = "2024spring")
                })
                DropdownMenuItem(onClick = { selected = "Some long ass name" }, text = {
                    Text(text = "Some long ass name")
                })
            }
            Spacer(Modifier.size(32.dp))
            DropDown(label = "Other", value = selected, items = listOf("2024spring", "super long name", "2023fall")) {
                selected = it
            }
            Spacer(modifier = Modifier.size(32.dp))
            TextDropDown(label = "Time Period", value = selected) {
                DropdownMenuItem(onClick = { /*TODO*/ }, text = {
                    Text(text = "2024spring")
                })
                DropdownMenuItem(onClick = { /*TODO*/ }, text = {
                    Text(text = "2024fall")
                })
            }
            TextDropDown(label = "Other", value = selected, items = listOf("2024spring", "super long name", "2023fall")) {
                selected = it
            }

            TextDropDown(label = "Empty", value = "" ) {}
            DropDown(label = "Empty", value = "" ) {}
            TextDropDown(label = "Other", value = selected, items = emptyList()) {
                selected = it
            }
        }
    }
}