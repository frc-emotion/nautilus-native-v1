package org.team2658.emotion.android.screens.scouting.standscoutingforms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.team2658.emotion.android.ui.composables.ErrorAlertDialog
import org.team2658.emotion.android.ui.composables.SuccessAlertDialog
import org.team2658.emotion.android.viewmodels.SettingsViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel
import org.team2658.emotion.scouting.scoutingdata.RapidReact

@Composable
fun RapidReactForm(
    settingsViewModel: SettingsViewModel,
    scoutingViewModel: StandScoutingViewModel
) {
    var leftTarmac by remember { mutableStateOf<Boolean?>(null) }
    var autoLower by remember { mutableStateOf("") }
    var autoUpper by remember { mutableStateOf("") }
    var teleopLower by remember { mutableStateOf("") }
    var teleopUpper by remember { mutableStateOf("") }
    var cycleTime by remember { mutableStateOf("") }
    var shotLocation by remember { mutableStateOf("") }
    var climbScore by remember { mutableStateOf<ClimbScore>(ClimbScore.NONE) }
    var humanShot by remember { mutableStateOf<Boolean?>(null) }
    var cargoRP by remember { mutableStateOf<Boolean?>(null) }
    var hangarRP by remember { mutableStateOf<Boolean?>(null) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val inputOk = leftTarmac != null
            && autoLower.toIntOrNull() != null
            && autoUpper.toIntOrNull() != null
            && teleopLower.toIntOrNull() != null
            && teleopUpper.toIntOrNull() != null
            && cycleTime.trim().isNotBlank()
            && shotLocation.trim().isNotBlank()
            && humanShot != null
            && climbScore != ClimbScore.NONE
            && cargoRP != null
            && hangarRP != null
    val competitions = listOf("Beach Blitz 2022")

    //TODO: get competition list via API call through viewmodel
    BaseScoutingForm(
        competitions = competitions,
        onFormSubmit = { data, clear ->
            val success = 0 == scoutingViewModel.submitRapidReact(
                user = settingsViewModel.user,
                data = RapidReact(
                    //no need to check for null safety or validity of inputs here
                    //this function will only be called if inputOk is true
                    baseData = data,
                    leftTarmac = leftTarmac!!,
                    autoLower = autoLower.toInt(),
                    autoUpper = autoUpper.toInt(),
                    teleopLower = teleopLower.toInt(),
                    teleopUpper = teleopUpper.toInt(),
                    cycleTime = cycleTime,
                    shotLocation = shotLocation,
                    climbScore = climbScore.value,
                    humanShot = humanShot!!,
                    cargoRP = cargoRP!!,
                    hangarRP = hangarRP!!,
                )
            )
            if (success) {
                showSuccessDialog = true
                clear()
            } else {
                showErrorDialog = true
            }
        },
        contentInputsOkay = inputOk
    ) {
        //TODO
    }

    SuccessAlertDialog(show = showSuccessDialog) {
        showSuccessDialog = false
    }

    ErrorAlertDialog(show = showErrorDialog) {
        showErrorDialog = false
    }
}

enum class ClimbScore(val value: Int) {
    NONE(-1),
    ZERO(0),
    FOUR(4),
    SIX(6),
    TEN(10),
    FIFTEEN(15),
}