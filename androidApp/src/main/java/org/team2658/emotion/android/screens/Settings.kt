package org.team2658.emotion.android.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.team2658.emotion.AuthState
import org.team2658.emotion.android.MainTheme
import org.team2658.emotion.android.composables.LabelledTextBoxSingleLine


@Composable
fun SettingsScreen(authState: AuthState) {
    Surface(color= MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()){
        Scaffold{padding->
            Box(modifier=Modifier.padding(padding)) {
                Column(modifier= Modifier
                    .padding(32.dp)
                ) {
                    when(authState) {
                        AuthState.NOT_LOGGED_IN -> SettingsNotLoggedIn()
                        AuthState.AWAITING_VERIFICATION -> SettingsAwaitingVerification()
                        AuthState.LOGGED_IN -> SettingsLoggedIn("exampleuser")
                    }
                }
            }

        }
    }
}


enum class SignedOutScreen {
    LOGIN,
    REGISTER
}
@Composable
fun SettingsNotLoggedIn() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = SignedOutScreen.LOGIN.name){
        composable(SignedOutScreen.LOGIN.name){
            LoginScreen{
                navController.navigate(SignedOutScreen.REGISTER.name)
            }
        }
        composable(SignedOutScreen.REGISTER.name){
            RegisterScreen{
                navController.navigate(SignedOutScreen.LOGIN.name)
            }
        }
    }
}



@Composable
fun LoginScreen(
    onCreateAccount: ()->Unit
) {
    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    Column {
        Text(text = "Log In to Use App",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        LabelledTextBoxSingleLine(label = "Username",
            text = username,
            onValueChange = { text -> username = text}
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Password",
            text = password,
            onValueChange = {text -> password = text},
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row{
            Button(onClick = { /*TODO*/ }) {
                Text(text="Log In")
            }
            Spacer(modifier = Modifier.size(24.dp))
            ElevatedButton(onClick = { onCreateAccount() }) {
                Text(text="Create New Account")
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onLogin: ()->Unit
) {
    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var passwordConfirm by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var firstName by remember { mutableStateOf("")}
    var lastName by remember { mutableStateOf("")}

    Column(modifier = Modifier.verticalScroll(rememberScrollState(), true)) {
        Text(text = "Create New Account",
            style = MaterialTheme.typography.displayMedium,
        )
        Text(text = "After registering, please notify a team lead to verify your account",
            style = MaterialTheme.typography.bodyLarge,
            )
        Spacer(modifier = Modifier.size(32.dp))
        LabelledTextBoxSingleLine(label = "First Name",
            text = firstName,
            onValueChange = { text -> firstName = text}
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Last Name",
            text = lastName,
            onValueChange = { text -> lastName = text}
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Email",
            text = email,
            onValueChange = { text -> email = text},
             keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Username",
            text = username,
            onValueChange = { text -> username = text}
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Password",
            text = password,
            onValueChange = {text -> password = text},
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(label = "Confirm Password",
            text = passwordConfirm,
            onValueChange = {text -> passwordConfirm = text},
            keyboardType = KeyboardType.Password,
            innerLabel = "Confirm Password"
        )
        if(password != passwordConfirm) {
            Text(text = "Passwords do not match",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Row{
            Button(onClick = { /*TODO*/ }) {
                Text(text="Create Account")
            }
            Spacer(modifier = Modifier.size(24.dp))
            ElevatedButton(onClick = { onLogin() }) {
                Text(text="Log In to Existing Account")
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun SettingsAwaitingVerification() {
    Text(text = "Awaiting Verification",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.size(32.dp))
    Text(text = "Please contact a team lead to verify your account, then log in again.",
        style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.size(32.dp))
    Button(onClick = { /*TODO*/ }) {
        Text(text="Log Out")
    }
}

@Composable
fun SettingsLoggedIn(username: String) {
    Text(text = "Settings",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.size(32.dp))
    Text(text = "You are logged in as $username",
        style = MaterialTheme.typography.headlineLarge,
    )
    Spacer(modifier = Modifier.size(32.dp))
    Button(onClick = { /*TODO*/ }) {
        Text(text="Log Out")
    }
}


@Preview
@Composable
fun SettingsPreview() {
    MainTheme(false) {
        SettingsScreen(AuthState.NOT_LOGGED_IN)
    }
}