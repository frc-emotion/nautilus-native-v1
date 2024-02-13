package org.nautilusapp.nautilus.android.screens.settings.loggedout

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.network.Organization

@Composable
fun NotLoggedInScreen(
    onLogin: suspend (
        username: String,
        password: String,
        errorCallback: (String) -> Unit
    ) -> Unit,
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
    organization: Organization,
    orgs: List<Organization>,
    setOrganization: (Organization) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = SignedOutScreens.LOGIN.name) {
        composable(SignedOutScreens.LOGIN.name) {
            LoginScreen(onLogin = onLogin, organization = organization, orgs = orgs, onSetOrganization = setOrganization) {
                navController.navigate(SignedOutScreens.REGISTER.name)
            }
        }
        composable(SignedOutScreens.REGISTER.name) {
            RegisterScreen(onRegister) {
                navController.navigate(SignedOutScreens.LOGIN.name)
            }
        }
    }
}