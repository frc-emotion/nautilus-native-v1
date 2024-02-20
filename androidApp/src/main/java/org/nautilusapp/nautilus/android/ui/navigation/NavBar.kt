package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.nautilusapp.nautilus.toCapitalized
import org.nautilusapp.nautilus.userauth.AccountType
import org.nautilusapp.nautilus.userauth.UserPermissions

@Composable
fun NavBar(navController: NavController, accountType: AccountType?, permissions: UserPermissions?) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val items = when {
        (accountType == AccountType.ADMIN || accountType == AccountType.SUPERUSER || accountType == AccountType.LEAD) -> listOf(
            AppScreens.HOME,
//            AppScreens.SCOUTING,
//            AppScreens.LEADS,
            AppScreens.SETTINGS
        )

        (permissions?.generalScouting == true)
                || (permissions?.pitScouting == true)
                || (permissions?.viewScoutingData == true)
        -> listOf(
            AppScreens.HOME,
//            AppScreens.SCOUTING,
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