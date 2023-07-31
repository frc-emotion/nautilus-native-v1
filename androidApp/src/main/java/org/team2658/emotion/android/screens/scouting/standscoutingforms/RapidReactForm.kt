package org.team2658.emotion.android.screens.scouting.standscoutingforms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.YesNoSelector
import org.team2658.emotion.android.viewmodels.SettingsViewModel
import org.team2658.emotion.android.viewmodels.StandScoutingViewModel
import org.team2658.emotion.scouting.scoutingdata.RapidReact

@Composable
fun RapidReactForm(
    settingsViewModel: SettingsViewModel,
    scoutingViewModel: StandScoutingViewModel
) {
    var leftTarmac by rememberSaveable { mutableStateOf<Boolean?>(null) }
    //use rememberSaveable instead of remember so the state is saved if user changes tabs
    var autoLower by rememberSaveable { mutableStateOf("") }
    var autoUpper by rememberSaveable { mutableStateOf("") }
    var teleopLower by rememberSaveable { mutableStateOf("") }
    var teleopUpper by rememberSaveable { mutableStateOf("") }
    var cycleTime by rememberSaveable { mutableStateOf("") }
    var shotLocation by rememberSaveable { mutableStateOf("") }
    var climbScore by rememberSaveable { mutableStateOf(ClimbScore.NONE) }
    var humanShot by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var cargoRP by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var hangarRP by rememberSaveable { mutableStateOf<Boolean?>(null) }


    val inputOk = leftTarmac != null
            && autoLower.trim().toIntOrNull() != null
            && autoUpper.trim().toIntOrNull() != null
            && teleopLower.trim().toIntOrNull() != null
            && teleopUpper.trim().toIntOrNull() != null
            && cycleTime.trim().isNotBlank()
            && shotLocation.trim().isNotBlank()
            && humanShot != null
            && climbScore != ClimbScore.NONE
            && cargoRP != null
            && hangarRP != null


    val competitions = listOf("Beach Blitz 2022")
    //TODO: get competition list via API call through viewmodel

    fun clearForm() {
        leftTarmac = null
        autoLower = ""
        autoUpper = ""
        teleopLower = ""
        teleopUpper = ""
        cycleTime = ""
        shotLocation = ""
        climbScore = ClimbScore.NONE
        humanShot = null
        cargoRP = null
        hangarRP = null
    }

    Text(text = "Rapid React Scouting Form", style = MaterialTheme.typography.headlineLarge)
    Spacer(modifier = Modifier.size(16.dp))
    BaseScoutingForm(
        competitions = competitions,
        onFormSubmit = { data ->
            scoutingViewModel.submitRapidReact(
                user = settingsViewModel.user,
                data = RapidReact(
                    //no need to check for null safety or validity of inputs here
                    //this function will only be called if inputOk is true
                    baseData = data,
                    leftTarmac = leftTarmac!!,
                    autoLower = autoLower.trim().toInt(),
                    autoUpper = autoUpper.trim().toInt(),
                    teleopLower = teleopLower.trim().toInt(),
                    teleopUpper = teleopUpper.trim().toInt(),
                    cycleTime = cycleTime,
                    shotLocation = shotLocation,
                    climbScore = climbScore.value,
                    humanShot = humanShot!!,
                    cargoRP = cargoRP!!,
                    hangarRP = hangarRP!!,
                )
            )
        },
        contentInputsOkay = inputOk,
        clearContentInputs = ::clearForm
    ) {
        //rapid react specific inputs
        Text(text = "Auto", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        YesNoSelector(
            label = "Left Tarmac",
            value = leftTarmac,
            setValue = { leftTarmac = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Auto Lower Score",
            text = autoLower,
            onValueChange = { autoLower = it },
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(16.dp))
        //TODO: change to custom incrementer component
        LabelledTextBoxSingleLine(
            label = "Auto Upper Score",
            text = autoUpper,
            onValueChange = { autoUpper = it },
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Teleop", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Teleop Lower Score",
            text = teleopLower,
            onValueChange = { teleopLower = it },
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Teleop Upper Score",
            text = teleopUpper,
            onValueChange = { teleopUpper = it },
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Robot and Play Style", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Cycle Time",
            text = cycleTime,
            onValueChange = { cycleTime = it },
        )
        Spacer(modifier = Modifier.size(16.dp))
        LabelledTextBoxSingleLine(
            label = "Shot Location",
            text = shotLocation,
            onValueChange = { shotLocation = it },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Climb Score", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.size(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = climbScore == ClimbScore.ZERO,
                onClick = { climbScore = ClimbScore.ZERO })
            Text(text = "0", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = climbScore == ClimbScore.FOUR,
                onClick = { climbScore = ClimbScore.FOUR })
            Text(text = "4", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = climbScore == ClimbScore.SIX,
                onClick = { climbScore = ClimbScore.SIX })
            Text(text = "6", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = climbScore == ClimbScore.TEN,
                onClick = { climbScore = ClimbScore.TEN })
            Text(text = "10", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = climbScore == ClimbScore.FIFTEEN,
                onClick = { climbScore = ClimbScore.FIFTEEN })
            Text(text = "15", style = MaterialTheme.typography.labelLarge)
        }
        Spacer(modifier = Modifier.size(16.dp))
        YesNoSelector(
            label = "Human Shot",
            value = humanShot,
            setValue = { humanShot = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        YesNoSelector(
            label = "Cargo Ranking Point",
            value = cargoRP,
            setValue = { cargoRP = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        YesNoSelector(
            label = "Hangar Ranking Point",
            value = hangarRP,
            setValue = { hangarRP = it }
        )
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