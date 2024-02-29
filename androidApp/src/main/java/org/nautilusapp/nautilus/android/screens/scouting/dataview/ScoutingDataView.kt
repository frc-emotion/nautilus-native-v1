package org.nautilusapp.nautilus.android.screens.scouting.dataview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo

@Composable
fun CrescendoDataViewScreen(
    dh: DataHandler,
    snack: SnackbarHostState
) {
    var detail: Pair<Int, Map<Int, List<Crescendo>>>? by remember { mutableStateOf(null) }
    val nav = rememberNavController()
    LaunchedEffect(detail) {
        if (detail == null) nav.navigate(DataViewScreens.Standard.name)
    }
    NavHost(navController = nav, startDestination = DataViewScreens.Standard.name) {
        composable(DataViewScreens.Standard.name) {
            ScoutingDataListScreen(dh = dh, snack = snack, onItemClicked = {
                detail = it
                nav.navigate(DataViewScreens.Detail.name) {
                    popUpTo(nav.graph.startDestinationId)
                }
            })
        }
        composable(DataViewScreens.Detail.name) {
            detail?.let {
                CrescendoDetailView(
                    teamnumber = it.first,
                    data = it.second,
                    snack = snack,
                    getUsername = { u ->
                        dh.users.getOne(u)?.username
                    },
                    onRefresh = { dh.crescendo.sync().download }) {
                    detail = null
                    nav.navigate(DataViewScreens.Standard.name) {
                        popUpTo(nav.graph.startDestinationId)
                    }
                }
            }
        }
    }
}

enum class DataViewScreens {
    Standard,
    Detail
}