package org.nautilusapp.nautilus.android.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.screens.home.admin.AttendanceVerificationPage
import org.nautilusapp.nautilus.android.ui.navigation.NestedScaffold
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    primaryViewModel: MainViewModel,
    snack: SnackbarHostState,
    dataHandler: DataHandler,
    nfcViewmodel: NFCViewmodel
) {
    val attendanceNavController = rememberNavController()
    NavHost(
        navController = attendanceNavController,
        startDestination = HomeScreens.ATTEND.name
    ) {
        composable(HomeScreens.ATTEND.name) {
            NestedScaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Attendance") },
                        actions = {
                            if (primaryViewModel.user?.permissions?.viewMeetings == true)
                                TextButton(
                                    onClick = {
                                        attendanceNavController.navigate(HomeScreens.MEETINGS.name)
                                    }) {
                                    Text("Manage Meetings")
                                }
                        }
                    )
                },
                snack = snack
            ) {
                AttendMeetingScreen(
                    nfcViewmodel = nfcViewmodel,
                    primaryViewModel = primaryViewModel,
                    snack = snack
                )
            }
        }
        composable(HomeScreens.MEETINGS.name) {
            AttendanceVerificationPage(
                primaryViewModel,
                nfcViewmodel,
                dataHandler = dataHandler,
                snack = snack,
                onNav = {
                    attendanceNavController.navigate(HomeScreens.ATTEND.name)
                }
            )
        }
    }
}