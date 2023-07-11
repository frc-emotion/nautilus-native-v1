package org.team2658.emotion.android.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

//composables for different text input boxes

@Composable
fun LabelledTextBoxSingleLine(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String)->Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    innerLabel: String = "Enter $label"
) {
    Column(modifier = modifier){

        Text(text=label, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier=Modifier.height(4.dp))
        OutlinedTextField(value = text, onValueChange = onValueChange,
            visualTransformation = if(keyboardType==KeyboardType.Password) PasswordVisualTransformation()
                else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            label = { Text(innerLabel) },
            singleLine = true
        )

    }
}