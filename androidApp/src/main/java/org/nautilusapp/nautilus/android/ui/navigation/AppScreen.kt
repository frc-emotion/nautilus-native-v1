package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppScreen(val icon: ImageVector) {
    HOME(Icons.Filled.Home),
    SCOUTING(Icons.Filled.Summarize),
    USERS(Icons.Filled.People),
    SETTINGS(Icons.Filled.Settings),
}