package org.team2658.emotion.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LabelledTextBoxSingleLine(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    innerLabel: String = "Enter $label",
    required: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default
) {
    var showError by remember { mutableStateOf(false) }
    Column(modifier = modifier) {

        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.onFocusChanged { if (!showError && it.isFocused) showError = true },
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            label = { Text(innerLabel) },
            singleLine = true,
            keyboardActions = keyboardActions,
            supportingText = {
                if (required && text.isBlank()) {
                    Text(text = "* Required")
                } else if (showError) when (keyboardType) {
                    KeyboardType.Phone,
                    KeyboardType.Number,
                    KeyboardType.NumberPassword -> {
                        if (text.toIntOrNull() == null) {
                            Text(
                                text = "Input must be a whole number",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    KeyboardType.Decimal -> {
                        if (text.toDoubleOrNull() == null) {
                            Text(
                                text = "Input must be a decimal number",
                            )
                        }
                    }

                    KeyboardType.Email -> {
                        if (!text.contains('@')) {
                            Text(
                                text = "Input must be a valid email",
                            )
                        }
                    }

                    else -> {}
                }
            },
            isError = showError && when (keyboardType) {
                KeyboardType.Phone,
                KeyboardType.Number,
                KeyboardType.NumberPassword -> {
                    text.toIntOrNull() == null
                }

                KeyboardType.Decimal -> {
                    text.toDoubleOrNull() == null
                }

                KeyboardType.Email -> {
                    !text.contains('@')
                }

                else -> {
                    required && text.isBlank()
                }
            }
        )

    }
}

@Composable
fun TextArea(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    innerLabel: String = "Enter $label"
) {
    Column(modifier = modifier) {

        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = text, onValueChange = onValueChange,
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            label = { Text(innerLabel) },
            singleLine = false
        )

    }
}