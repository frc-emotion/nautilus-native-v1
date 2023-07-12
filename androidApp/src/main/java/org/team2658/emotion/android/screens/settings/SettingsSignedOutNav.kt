package org.team2658.emotion.android.screens.settings

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.team2658.emotion.userauth.Subteam

@Composable
fun NotLoggedInScreen(
    onLogin: (
        username: String,
        password: String
    ) -> Unit,
    onRegister: (
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam
    ) -> Unit) {
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