package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine

@Composable
fun LoginScreen(
    onLogin: (
        username: String,
        password: String
            ) -> Unit,
    onCreateAccount: ()->Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        Text(
            text = "Log In to Use App",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        LabelledTextBoxSingleLine(label = "Username",
            text = username,
            onValueChange = { text -> username = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Password",
            text = password,
            onValueChange = { text -> password = text },
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row {
            Button(onClick = { onLogin(username, password) }) {
                Text(text = "Log In")
            }
            Spacer(modifier = Modifier.size(24.dp))
            ElevatedButton(onClick = { onCreateAccount() }) {
                Text(text = "Create New Account")
            }
        }
    }
}