package org.team2658.emotion.android.screens.settings

import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.LoginInput
import org.team2658.emotion.android.ui.composables.LoginType
import org.team2658.emotion.toCapitalized
import org.team2658.emotion.userauth.Subteam


@Composable
fun RegisterScreen(
    onRegister: suspend (
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam,
        phone: String,
        grade: Int,
        errorCallback: (String) -> Unit
    ) -> Unit,
    onLogin: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var subteam by rememberSaveable { mutableStateOf(Subteam.NONE) }
    var grade by rememberSaveable { mutableIntStateOf(-1) }
    var phone by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showError by remember {mutableStateOf(false)}
    var errorText by remember {mutableStateOf("")}
    if(showError) {
        AlertDialog(onDismissRequest = {  }, confirmButton = { TextButton(onClick = { showError = false })  {
            Text("Ok")
        }}, title = { Text("Error") }, text = { Text("Something went wrong\n $errorText") })
    }

    Column {
        Text(
            text = "Create New Account",
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = "After registering, please notify a team lead to verify your account",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.size(32.dp))
        LabelledTextBoxSingleLine(label = "First Name",
            text = firstName,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { text -> firstName = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Last Name",
            text = lastName,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { text -> lastName = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Email",
            text = email,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { text -> email = text },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Phone Number",
            text = phone,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { phone = it },
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Username",
            text = username,
            required = true,
            imeAction = ImeAction.Next,
            onValueChange = { text -> username = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
//        LabelledTextBoxSingleLine(
//            label = "Password",
//            text = password,
//            required = true,
//            imeAction = ImeAction.Next,
//            onValueChange = { text -> password = text },
//            keyboardType = KeyboardType.Password
//        )
        LoginInput(type = LoginType.PASSWORD, text = password, onValueChange = { password = it } )
        Spacer(modifier = Modifier.size(16.dp))
//        LabelledTextBoxSingleLine(
//            label = "Confirm Password",
//            text = passwordConfirm,
//            required = true,
//            onValueChange = { text -> passwordConfirm = text },
//            keyboardType = KeyboardType.Password,
//            innerLabel = "Confirm Password"
//        )
        LoginInput(type = LoginType.CONFIRM_PASSWORD, text = passwordConfirm, onValueChange = { passwordConfirm = it } )
        if (password != passwordConfirm) {
            Text(
                text = "Passwords do not match",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Subteam", style = MaterialTheme.typography.labelLarge)
        for (entry in Subteam.values().slice(1 until Subteam.values().size)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = (entry == subteam), onClick = { subteam = entry })
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = entry.name.toCapitalized(), style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
        Text(text = "Grade", style = MaterialTheme.typography.labelLarge)
        for (i in 9..12) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = (i == grade), onClick = { grade = i })
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "Grade $i", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = {
            if (password == passwordConfirm) {
                scope.launch {
                    onRegister(
                        username,
                        password,
                        email,
                        firstName,
                        lastName,
                        subteam,
                        phone,
                        grade)
                        { errorText = it; showError = true }
                }
            } else {
                //TODO: ALERT
                AlertDialog.Builder(context)
                    .setTitle("Invalid Input")
                    .setMessage("Please check all fields and try again")
                    .setNeutralButton("OK", null)
                    .show()
            }
        }) {
            Text(text = "Create Account")
        }
        Spacer(modifier = Modifier.size(8.dp))
        TextButton(onClick = { onLogin() }) {
            Text(text = "Log In to Existing Account")
        }
        Spacer(modifier = Modifier.height(300.dp))
    }
}
