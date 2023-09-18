package org.team2658.emotion.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppScreens(val icon: ImageVector) {
    HOME(Icons.Filled.Home),
    SETTINGS(Icons.Filled.Settings),
    SCOUTING(Icons.Filled.Summarize),
    ADMIN(Icons.Filled.AdminPanelSettings),
}