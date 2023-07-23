package org.team2658.emotion.android.screens.settings

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.MainTheme
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.toCapitalized
import org.team2658.emotion.userauth.Subteam
import java.lang.Integer.parseInt


@Composable
fun RegisterScreen(
    onRegister: (
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam,
        grade: Int,
        phone: Int,
    ) -> Unit,
    onLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var subteam by remember { mutableStateOf(Subteam.NONE) }
    var grade by remember { mutableStateOf(-1) }
    var phone by remember { mutableStateOf("") }
    val context = LocalContext.current

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
            onValueChange = { text -> firstName = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Last Name",
            text = lastName,
            onValueChange = { text -> lastName = text }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Email",
            text = email,
            onValueChange = { text -> email = text },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Phone Number",
            text = phone.toString(),
            onValueChange = { text -> phone = text },
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.size(16.dp))
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
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Confirm Password",
            text = passwordConfirm,
            onValueChange = { text -> passwordConfirm = text },
            keyboardType = KeyboardType.Password,
            innerLabel = "Confirm Password"
        )
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
                Checkbox(checked = (entry == subteam), onCheckedChange = { subteam = entry })
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
                Checkbox(checked = (i == grade), onCheckedChange = { grade = i })
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "Grade $i", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = {
            if (password == passwordConfirm && phone.toIntOrNull() !== null) {
                onRegister(
                    username,
                    password,
                    email,
                    firstName,
                    lastName,
                    subteam,
                    grade,
                    parseInt(phone)
                )
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

@Preview
@Composable
fun RegisterPreview() {
    MainTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            RegisterScreen(onRegister = { _, _, _, _, _, _, _, _ -> }, onLogin = { })
        }
    }

}
