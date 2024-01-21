package org.team2658.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
            modifier = Modifier
                .onFocusChanged {
                    if (!showError && it.isFocused) showError = true
                },
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            label = { Text(innerLabel) },
            singleLine = true,
            keyboardActions = keyboardActions,
            supportingText = {
                if (required && text.isBlank()) {
                    Text(text = "* Required")
                } else if (showError) when (keyboardType) {
                    KeyboardType.Number,
                    KeyboardType.NumberPassword -> {
                        if (text.toLongOrNull() == null) {
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
                KeyboardType.Number,
                KeyboardType.NumberPassword -> {
                    text.toLongOrNull() == null
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginInput(
    type: LoginType,
    text: String,
    onValueChange: (String) -> Unit,
) {
    //AutoFill Handling and text field configuration based on input type
    val (keyboardType, autoFillTypes) = when (type) {
        LoginType.PASSWORD, LoginType.CONFIRM_PASSWORD -> Pair(KeyboardType.Password, listOf(AutofillType.Password))
        LoginType.USERNAME_OR_EMAIL -> Pair(KeyboardType.Email, listOf(AutofillType.EmailAddress, AutofillType.Username))
    }
    val label = when (type) {
        LoginType.PASSWORD -> "Password"
        LoginType.CONFIRM_PASSWORD -> "Confirm Password"
        LoginType.USERNAME_OR_EMAIL -> "Username or Email"
    }

    val autoFillNode = AutofillNode(autofillTypes = autoFillTypes, onFill= { onValueChange(it)} )
    val autofill = LocalAutofill.current
    LocalAutofillTree.current += autoFillNode


    //Controlling visibility of text
    var passwordHidden by remember { mutableStateOf(true) }
    val textHidden = passwordHidden && type != LoginType.USERNAME_OR_EMAIL

    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .onGloballyPositioned { autoFillNode.boundingBox = it.boundsInWindow() }
                .onFocusChanged { state ->
                    autofill?.run {
                        if (state.isFocused) {
                            requestAutofillForNode(autoFillNode)
                        }
                        else {
                            cancelAutofillForNode(autoFillNode)
                        }
                    }
                },
            visualTransformation = if (textHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
            label = { Text(label) },
            singleLine = true,
//            keyboardActions = keyboardActions,
            trailingIcon = {
                if(type != LoginType.USERNAME_OR_EMAIL) {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        Icon(
                            if (passwordHidden) Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility,
                            contentDescription = "Toggle Password Visibility",
//                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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

@Composable
fun NumberInput(
    label: String,
    value: Int?,
    modifier: Modifier = Modifier,
    onValueChange: (Int?) -> Unit,
    placeholder: String = label,
    required: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default,
    incrementBy: Int = 1,
    minValue: Int = 0,
    maxValue: Int = 255,
) {
    var showError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = if (value !== null) value.toString() else "",
            onValueChange = {
                if (it.isBlank()) {
                    onValueChange(null)
                }
                if (it.toIntOrNull() != null && (it.toInt() in minValue..maxValue)
                ) {
                    onValueChange(it.toInt())
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    if (!showError && it.isFocused) showError = true
                }
                .width(IntrinsicSize.Max),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true,
            keyboardActions = keyboardActions,
            supportingText = {
                if (required && (value == null)) {
                    Text(text = "* Required")
                }
            },
            isError = showError && required && (value == null),
            leadingIcon = {
                IconButton(onClick = {
                    if (value != null && (value in (minValue + 1) until maxValue)) onValueChange(
                        value - incrementBy
                    )
                }) {
                    Icon(
                        Icons.Filled.RemoveCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (value == null || (value in (minValue) until maxValue)) onValueChange(
                        if(value == null) incrementBy else (value + incrementBy)
                    )
                }) {
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
//            suffix = {
//                Button(onClick = {
//                    if (value == null || (value in (minValue + 1) until maxValue)) onValueChange(
//                        (value ?: 0) + incrementBy
//                    )
//                }) {
//                    Text("+")
//                }
//            },
//            prefix = {
//                Button(
//                    onClick = {
//                        if (value != null && (value in (minValue + 1) until maxValue)) onValueChange(
//                            value - incrementBy
//                        )
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//                ) {
//                    Text("-")
//                }
//            }
        )

    }
}

@Composable
fun NumberInput(
    label: String,
    value: Double?,
    modifier: Modifier = Modifier,
    onValueChange: (Double?) -> Unit,
    placeholder: String = label,
    required: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default,
    incrementBy: Double = 1.0,
    minValue: Double = 0.0,
    maxValue: Double = 255.0,
) {
    var showError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = if (value !== null) value.toString() else "",
            onValueChange = {
                if (it.isBlank()) {
                    onValueChange(null)
                }
                if (it.toDoubleOrNull() != null && it.toDouble() > minValue && it.toDouble() < maxValue
                ) {
                    onValueChange(it.toDouble())
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    if (!showError && it.isFocused) showError = true
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = imeAction
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.Center,
                )
            },
            singleLine = true,
            keyboardActions = keyboardActions,
            supportingText = {
                if (required && (value == null)) {
                    Text(text = "* Required")
                }
            },
            isError = showError && required && (value == null),
            leadingIcon = {
                IconButton(onClick = {
                    if (value != null && value < maxValue && value > minValue) onValueChange(
                        value - incrementBy
                    )
                }) {
                    Icon(
                        Icons.Filled.RemoveCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (value == null || (value < maxValue && value > minValue)) onValueChange(
                        (value ?: 0.0) + incrementBy
                    )
                }) {
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
//            suffix = {
//                Button(onClick = {
//                    if (value == null || (value in (minValue + 1) until maxValue)) onValueChange(
//                        (value ?: 0) + incrementBy
//                    )
//                }) {
//                    Text("+")
//                }
//            },
//            prefix = {
//                Button(
//                    onClick = {
//                        if (value != null && (value in (minValue + 1) until maxValue)) onValueChange(
//                            value - incrementBy
//                        )
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//                ) {
//                    Text("-")
//                }
//            }
        )

    }
}

enum class LoginType {
    PASSWORD,
    CONFIRM_PASSWORD,
    USERNAME_OR_EMAIL,
}