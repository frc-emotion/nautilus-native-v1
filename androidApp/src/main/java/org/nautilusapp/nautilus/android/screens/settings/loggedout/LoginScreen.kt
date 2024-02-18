package org.nautilusapp.nautilus.android.screens.settings.loggedout

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.composables.LoginInput
import org.nautilusapp.nautilus.android.ui.composables.LoginType
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.TextDropDown
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.network.Organization

@Composable
fun LoginScreen(
    onLogin: suspend (
        username: String,
        password: String,
        errorCallback: (String) -> Unit
    ) -> Unit,
    organization: Organization,
    orgs: List<Organization>,
    onSetOrganization: (Organization) -> Unit,
    onCreateAccount: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
            text = "Log In",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        TextDropDown(label = "Organization", value = organization, items = orgs, onValueChange = { onSetOrganization(it) }, getStr = { it.name })
        Spacer(modifier = Modifier.size(8.dp))
        LoginInput(type = LoginType.USERNAME_OR_EMAIL, text = username, onValueChange = { username = it })
        Spacer(modifier = Modifier.size(16.dp))
        LoginInput(type = LoginType.PASSWORD, text = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            Button(onClick = { scope.launch { onLogin(username, password) { showError = true; errorText = it } } }) {
                Text(text = "Log In")
            }
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedButton(onClick = { onCreateAccount() }) {
                Text(text = "Register")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        TextButton(onClick = {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("${organization.url}/pages/forgot-password"))
            try {
                context.startActivity(webIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }) {
            Text(text = "Forgot your password?")
        }
    }
}

@Composable
@Preview(apiLevel = 33)
fun LoginScreenPreview() {
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        Screen {
            LoginScreen(onLogin = {_, _, _: (String) -> Unit ->  }, organization = Organization("Team 2658", ""), orgs = listOf(Organization("Team 2658", "")) , onSetOrganization = {_ -> } ) {}
        }
    }
}