package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

@Composable
fun DropDown(label: String, value: String, children: @Composable () -> Unit) {
    var open by remember { mutableStateOf(false) }
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(8.dp))
    Box() {
        OutlinedButton(onClick = { open = !open }, modifier = Modifier.defaultMinSize(minWidth = 196.dp, minHeight = 0.dp)) {
            if(value.isBlank()) {
                Text(text = label, color = MaterialTheme.colorScheme.outline)
            } else {
                Text(text = value)
            }
            Icon(Icons.Outlined.ExpandMore, contentDescription = "More",
                tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            children()
        }
    }
}

@Composable
fun TextDropDown(label: String, value: String, children: @Composable () -> Unit) {
    var open by remember { mutableStateOf(false) }
    Text(text = label, style = MaterialTheme.typography.labelLarge)
//    Spacer(modifier = Modifier.size(4.dp)
    Box() {
        TextButton(onClick = { open = !open }) {
            if(value.isBlank()) {
                Text(text = label, color = MaterialTheme.colorScheme.outline)
            } else {
                Text(text = value)
            }
            Icon(Icons.Outlined.ExpandMore, contentDescription = "More",
                tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            children()
        }
    }
}

@Composable
fun TextDropDown(label: String, value: String, items: List<String>, onValueChange: (String) -> Unit ) {
    var open by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(4.dp))
        Box() {
            TextButton(onClick = { open = !open }, enabled = items.isNotEmpty()) {
                if(value.isBlank()) {
                    Text(text = label, color = MaterialTheme.colorScheme.outline)
                } else {
                    Text(text = value)
                }
                Icon(Icons.Outlined.ExpandMore, contentDescription = "More",
                    tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
            }
            DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                items.forEach {
                    DropdownMenuItem(onClick = { onValueChange(it); open = false }, text = {
                        Text(text = it)
                    })
                }
            }

        }
    }

}

@Composable
fun DropDown(label: String, value: String, items: List<String>, onValueChange: (String) -> Unit) {
    var open by remember { mutableStateOf(false) }
    Text(text = label, style = MaterialTheme.typography.labelLarge)
    Box() {
        OutlinedButton(onClick = { open = !open }, enabled = items.isNotEmpty(),
            modifier = Modifier.defaultMinSize(minWidth = 196.dp, minHeight = 0.dp)) {
            if(value.isBlank()) {
                Text(text = label, color = MaterialTheme.colorScheme.outline)
            } else {
                Text(text = value)
            }
            Icon(Icons.Outlined.ExpandMore, contentDescription = "More",
                tint = if(value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            items.forEach {
                DropdownMenuItem(onClick = { onValueChange(it); open = false }, text = {
                    Text(text = it)
                })
            }
        }

    }
}

@Composable
@Preview
fun DropDownPreview() {
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