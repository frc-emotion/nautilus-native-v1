package org.team2658.emotion.android.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine

@Composable
fun LoginScreen(
    onLogin: suspend (
        username: String,
        password: String,
        errorCallback: (String) -> Unit
    ) -> Unit,
    onCreateAccount: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showError by remember {mutableStateOf(false)}
    var errorText by remember {mutableStateOf("")}
    if(showError) {
        AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showError = false })  {
            Text("Ok")
        }}, title = { Text("Error") }, text = { Text("Something went wrong\n $errorText") })
    }
    Column {
        Text(
            text = "Log In to Use App",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        LabelledTextBoxSingleLine(label = "Username",
            text = username,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { text -> username = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Password",
            text = password,
            required = true,
            onValueChange = { text -> password = text },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go,
            keyboardActions = KeyboardActions(onGo = {
                runBlocking {
                    onLogin(
                        username,
                        password
                    ){
                        showError = true
                        errorText = it
                    }
                }
            })
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = { runBlocking { onLogin(username, password) {showError = true; errorText = it} } }) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.size(8.dp))
        TextButton(onClick = { onCreateAccount() }) {
            Text(text = "Create New Account")
        }
    }
}