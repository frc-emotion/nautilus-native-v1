package org.nautilusapp.nautilus.android.screens.settings

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.userauth.Subteam

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
    ) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = SignedOutScreens.LOGIN.name) {
        composable(SignedOutScreens.LOGIN.name) {
            LoginScreen(onLogin) {
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