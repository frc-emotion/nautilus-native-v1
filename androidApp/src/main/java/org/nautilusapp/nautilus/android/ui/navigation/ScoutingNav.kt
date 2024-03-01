@file:OptIn(ExperimentalMaterial3Api::class)

package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.screens.scouting.dataview.CrescendoDataViewScreen
import org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.CrescendoForm

@Composable
fun ScoutingNav(dh: DataHandler, snack: SnackbarHostState) {
    val nav = rememberNavController()
    val bs by nav.currentBackStackEntryAsState()
    val route = bs?.destination?.route ?: ScoutingScreens.CrescendoForm.name
    val screen = try {
        ScoutingScreens.valueOf(route)
    } catch (_: Exception) {
        ScoutingScreens.CrescendoForm
    }

    Column(Modifier.fillMaxWidth()) {
        SecondaryTabRow(
            selectedTabIndex = screen.index,
        ) {
            ScoutingScreens.entries.forEach {
                Tab(selected = it == screen, onClick = { nav.navigate(it.name) },
                    text = {
                        Text(text = it.displayName)
                    }
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        NavHost(navController = nav, startDestination = ScoutingScreens.CrescendoForm.name) {
            composable(ScoutingScreens.CrescendoForm.name) {
                CrescendoForm(dh = dh, snack = snack)
            }

            composable(ScoutingScreens.CrescendoView.name) {
                CrescendoDataViewScreen(dh = dh, snack = snack)
            }
        }
    }
}

enum class ScoutingScreens(val index: Int, val displayName: String) {
    CrescendoForm(0, "Scouting"),
    CrescendoView(1, "Results")
}