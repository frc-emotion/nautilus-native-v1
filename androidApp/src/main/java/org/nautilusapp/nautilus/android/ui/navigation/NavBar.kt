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
import org.nautilusapp.nautilus.userauth.TokenUser

@Composable
fun NavBar(navController: NavController, user: TokenUser?) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        AppScreen.entries.forEach {
            if (user?.canViewScreen(it) == true) {
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
}

fun TokenUser?.canViewScreen(screen: AppScreen): Boolean {
    if (screen == AppScreen.SCOUTING && !this.canViewScoutingPage) return false;
    return true
}