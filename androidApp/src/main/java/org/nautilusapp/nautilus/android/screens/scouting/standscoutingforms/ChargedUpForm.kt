package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms
//
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import org.org.nautilusapp.nautilus.android.ui.composables.NumberInput
//import org.org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
//import org.org.nautilusapp.nautilus.android.viewmodels.UserViewModel
//
//@Composable
//fun ChargedUpForm(primaryViewModel: UserViewModel) {
//    var autoBotCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoBotCones by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoMidCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoMidCones by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoTopCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoTopCones by rememberSaveable { mutableStateOf<Int?>(null) }
//
//    var teleopBotCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopBotCones by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopMidCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopMidCones by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopTopCubes by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopTopCones by rememberSaveable { mutableStateOf<Int?>(null) }
//
//    var linkScore: Int? by rememberSaveable { mutableStateOf(null) }
//
//    var autoChargeStationState: AutoChargeStationState? by rememberSaveable { mutableStateOf(null) }
//    val autoDocked: Boolean? = when(autoChargeStationState) {
//        null -> null
//        AutoChargeStationState.NOT_DOCKED -> false
//        AutoChargeStationState.DOCKED_ENGAGED, AutoChargeStationState.DOCKED_NOT_ENGAGED -> true
//    }
//    val autoEngage: Boolean? = when(autoChargeStationState) {
//        null -> null
//        AutoChargeStationState.DOCKED_ENGAGED -> true
//        AutoChargeStationState.NOT_DOCKED, AutoChargeStationState.DOCKED_NOT_ENGAGED -> false
//    }
//
//
//    var teleopChargeStationState: TeleopChargeStationState? by rememberSaveable { mutableStateOf(null) }
//    val parked: Boolean? = when(teleopChargeStationState){
//        null-> null
//        TeleopChargeStationState.NOT_PARKED -> false
//        TeleopChargeStationState.PARKED_NOT_DOCKED, TeleopChargeStationState.DOCKED_ENGAGED, TeleopChargeStationState.DOCKED_NOT_ENGAGED -> true
//    }
//    val teleopDocked:Boolean? = when(teleopChargeStationState) {
//        null -> null
//        TeleopChargeStationState.NOT_PARKED, TeleopChargeStationState.PARKED_NOT_DOCKED -> false
//        TeleopChargeStationState.DOCKED_NOT_ENGAGED, TeleopChargeStationState.DOCKED_ENGAGED -> true
//    }
//    val teleopEngage:Boolean? = when(teleopChargeStationState) {
//        null -> null
//        TeleopChargeStationState.DOCKED_ENGAGED -> true
//        TeleopChargeStationState.DOCKED_NOT_ENGAGED, TeleopChargeStationState.NOT_PARKED, TeleopChargeStationState.PARKED_NOT_DOCKED -> false
//    }
//
//
//
//    var competitions:List<String> by rememberSaveable { mutableStateOf(listOf()) }
//    LaunchedEffect(Unit) {
//        competitions = primaryViewModel.getCompetitions("2023")
//    }
//
//    var sustainBonus: Boolean? by rememberSaveable{ mutableStateOf(null) }
//    var activationBonus: Boolean? by rememberSaveable{ mutableStateOf(null) }
//
//    fun clearForm() {
//        autoBotCubes = null
//        autoBotCones = null
//        autoMidCones = null
//        autoMidCubes = null
//        autoTopCubes = null
//        autoTopCones = null
//        teleopBotCones = null
//        teleopBotCubes = null
//        teleopMidCones = null
//        teleopMidCubes = null
//        teleopTopCones = null
//        teleopTopCubes = null
//        linkScore = null
//        autoChargeStationState = null
//        sustainBonus = null
//        activationBonus = null
//        teleopChargeStationState = null
//    }
//
//    val inputOk = autoBotCones != null &&
//            autoBotCubes != null &&
//            autoMidCones != null &&
//            autoMidCubes != null &&
//            autoTopCones != null &&
//            autoTopCubes != null &&
//            teleopBotCones != null &&
//            teleopBotCubes != null &&
//            teleopMidCones != null &&
//            teleopMidCubes != null &&
//            teleopTopCones != null &&
//            teleopTopCubes != null &&
//            linkScore != null &&
//            autoChargeStationState != null &&
//            teleopChargeStationState != null &&
//            sustainBonus != null &&
//            activationBonus != null
//
//    Text(text = "Charged Up Scouting Form", style = MaterialTheme.typography.headlineLarge)
//    Spacer(modifier = Modifier.size(16.dp))
//    BaseScoutingForm(
//        competitions = competitions,
//        onFormSubmit = { _ -> false
////                        primaryViewModel.submitChargedUp(primaryViewModel.user,
////                           ChargedUp(baseData = it,
////                               autoPeriod = ChargedUpScores(
////                                   botCones = autoBotCones!!,
////                                   botCubes = autoBotCubes!!,
////                                   midCones = autoMidCones!!,
////                                   midCubes = autoMidCubes!!,
////                                   topCones = autoTopCones!!,
////                                   topCubes = autoTopCubes!!
////                               ),
////                               teleopPeriod = ChargedUpScores(
////                                   botCubes = teleopBotCubes!!,
////                                   botCones = teleopBotCones!!,
////                                   midCones = teleopMidCones!!,
////                                   midCubes = teleopMidCubes!!,
////                                   topCones = teleopTopCones!!,
////                                   topCubes = teleopTopCubes!!
////                               ),
////                               linkScore = linkScore!!,
////                               autoDock = autoDocked!!,
////                               autoEngage = autoEngage!!,
////                               teleopDock = teleopDocked!!,
////                               teleopEngage = teleopEngage!!,
////                               parked = parked!!,
////                               RPEarned = listOf(sustainBonus!!, activationBonus!!)
////                               )
////                       )
//        },
//        contentInputsOkay = inputOk,
//        clearContentInputs = ::clearForm )
//    {
//        Text(text = "Auto Charge Station", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.size(8.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = autoChargeStationState == AutoChargeStationState.NOT_DOCKED,
//                onClick = { autoChargeStationState = AutoChargeStationState.NOT_DOCKED})
//            Spacer(Modifier.size(8.dp))
//            Text("Not Docked")
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = autoChargeStationState == AutoChargeStationState.DOCKED_NOT_ENGAGED,
//                onClick = {autoChargeStationState = AutoChargeStationState.DOCKED_NOT_ENGAGED} )
//            Spacer(Modifier.size(8.dp))
//            Text("Docked but Not Engaged")
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = autoChargeStationState == AutoChargeStationState.DOCKED_ENGAGED,
//                onClick = {autoChargeStationState = AutoChargeStationState.DOCKED_ENGAGED} )
//            Spacer(Modifier.size(8.dp))
//            Text("Docked and Engaged")
//        }
//        Spacer(Modifier.size(16.dp))
//        Text(text = "Auto Scores", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.size(8.dp))
//        Text(text = "Bottom: ${(autoBotCones?:0) + (autoBotCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(modifier = Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = autoBotCones, onValueChange = {autoBotCones = it}, modifier = Modifier.weight(1f) )
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = autoBotCubes, onValueChange = {autoBotCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Text(text = "Middle: ${(autoMidCones?:0) + (autoMidCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = autoMidCones, onValueChange = {autoMidCones = it}, modifier = Modifier.weight(1f))
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = autoMidCubes, onValueChange = {autoMidCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Text(text = "Top: ${(autoTopCones?:0) + (autoTopCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = autoTopCones, onValueChange = {autoTopCones = it}, modifier = Modifier.weight(1f))
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = autoTopCubes, onValueChange = {autoTopCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Button(onClick = {
//            if(autoBotCubes == null) autoBotCubes = 0
//            if(autoBotCones == null) autoBotCones = 0
//            if(autoMidCubes == null) autoMidCubes = 0
//            if(autoMidCones == null) autoMidCones = 0
//            if(autoTopCones == null) autoTopCones = 0
//            if(autoTopCubes == null) autoTopCubes = 0
//        }) {
//            Text("Set Blank Scores to 0")
//        }
//        Spacer(Modifier.size(16.dp))
//        Text(text = "Teleop Scores", style = MaterialTheme.typography.headlineSmall)
//        Spacer(modifier = Modifier.size(8.dp))
//        Text(text = "Bottom: ${(teleopBotCones?:0) + (teleopBotCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(modifier = Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = teleopBotCones, onValueChange = {teleopBotCones = it}, modifier = Modifier.weight(1f) )
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = teleopBotCubes, onValueChange = {teleopBotCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Text(text = "Middle: ${(teleopMidCones?:0) + (teleopMidCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = teleopMidCones, onValueChange = {teleopMidCones = it}, modifier = Modifier.weight(1f))
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = teleopMidCubes, onValueChange = {teleopMidCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Text(text = "Top: ${(teleopTopCones?:0) + (teleopTopCubes?:0)}", style = MaterialTheme.typography.bodyLarge)
//        Spacer(Modifier.size(4.dp))
//        Row {
//            NumberInput(label = "Cones", value = teleopTopCones, onValueChange = {teleopTopCones = it}, modifier = Modifier.weight(1f))
//            Spacer(Modifier.size(4.dp))
//            NumberInput(label = "Cubes", value = teleopTopCubes, onValueChange = {teleopTopCubes = it}, modifier = Modifier.weight(1f) )
//        }
//        Spacer(modifier = Modifier.size(4.dp))
//        Button(onClick = {
//            if(teleopBotCubes == null) teleopBotCubes = 0
//            if(teleopBotCones == null) teleopBotCones = 0
//            if(teleopMidCubes == null) teleopMidCubes = 0
//            if(teleopMidCones == null) teleopMidCones = 0
//            if(teleopTopCones == null) teleopTopCones = 0
//            if(teleopTopCubes == null) teleopTopCubes = 0
//        }) {
//            Text("Set Blank Scores to 0")
//        }
//        Spacer(Modifier.size(16.dp))
//        NumberInput(label = "Link Score", value = linkScore, onValueChange = {linkScore = it }, incrementBy = 5)
//        Spacer(Modifier.size(16.dp))
//        Text(text = "Teleop Charge Station", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.size(8.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = teleopChargeStationState == TeleopChargeStationState.NOT_PARKED,
//                onClick = { teleopChargeStationState = TeleopChargeStationState.NOT_PARKED})
//            Spacer(Modifier.size(8.dp))
//            Text("Not Parked")
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = teleopChargeStationState == TeleopChargeStationState.PARKED_NOT_DOCKED,
//                onClick = { teleopChargeStationState = TeleopChargeStationState.PARKED_NOT_DOCKED})
//            Spacer(Modifier.size(8.dp))
//            Text("Parked but Not Docked or Engaged")
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = teleopChargeStationState == TeleopChargeStationState.DOCKED_NOT_ENGAGED,
//                onClick = { teleopChargeStationState = TeleopChargeStationState.DOCKED_NOT_ENGAGED})
//            Spacer(Modifier.size(8.dp))
//            Text("Parked and Docked but Not Engaged")
//        }
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(selected = teleopChargeStationState == TeleopChargeStationState.DOCKED_ENGAGED,
//                onClick = { teleopChargeStationState = TeleopChargeStationState.DOCKED_ENGAGED})
//            Spacer(Modifier.size(8.dp))
//            Text("Parked, Docked, and Engaged")
//        }
//        Spacer(Modifier.size(16.dp))
//        Text(text="Ranking Points", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.size(16.dp))
//        YesNoSelector(label = "Sustainability Bonus?", value = sustainBonus, setValue = {sustainBonus = it })
//        Spacer(Modifier.size(8.dp))
//        YesNoSelector(label = "Activation Bonus", value = activationBonus, setValue = {activationBonus = it})
//
//    }
//}
//
//enum class AutoChargeStationState {
//    NOT_DOCKED,
//    DOCKED_NOT_ENGAGED,
//    DOCKED_ENGAGED
//}
//
//enum class TeleopChargeStationState {
//    NOT_PARKED,
//    PARKED_NOT_DOCKED,
//    DOCKED_NOT_ENGAGED,
//    DOCKED_ENGAGED
//}