package org.team2658.emotion.android.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.team2658.emotion.android.viewmodels.SettingsViewModel
import org.team2658.emotion.toCapitalized
import org.team2658.emotion.userauth.AccessLevel

@Composable
fun NavBar(navController: NavController, viewModel: SettingsViewModel) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val items = when {
        (viewModel.user?.accessLevel == AccessLevel.ADMIN) -> listOf(
            AppScreens.HOME,
            AppScreens.SCOUTING,
            AppScreens.ADMIN,
            AppScreens.SETTINGS
        )

        (viewModel.user?.permissions?.submitScoutingData == true)
                || (viewModel.user?.permissions?.inPitScouting == true)
                || (viewModel.user?.permissions?.viewScoutingData == true)
        -> listOf(
            AppScreens.HOME,
            AppScreens.SCOUTING,
            AppScreens.SETTINGS
        )

        else -> listOf(AppScreens.HOME, AppScreens.SETTINGS)
    }
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { dest -> dest.route == it.name } == true,
                onClick = {
                    navController.navigate(it.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(it.icon, contentDescription = it.name.toCapitalized())
                },
                label = {
                    Text(text = it.name.toCapitalized())
                }
            )
        }
    }
}