package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
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
//import org.org.nautilusapp.nautilus.android.ui.composables.LabelledTextBoxSingleLine
//import org.org.nautilusapp.nautilus.android.ui.composables.NumberInput
//import org.org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
//import org.org.nautilusapp.nautilus.android.viewmodels.UserViewModel
//
//@Composable
//fun RapidReactForm(
//    primaryViewModel: UserViewModel,
//) {
//    var leftTarmac by rememberSaveable { mutableStateOf<Boolean?>(null) }
//    //rememberSaveable instead of remember so the state is saved if user changes tabs
//    var autoLower by rememberSaveable { mutableStateOf<Int?>(null) }
//    var autoUpper by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopLower by rememberSaveable { mutableStateOf<Int?>(null) }
//    var teleopUpper by rememberSaveable { mutableStateOf<Int?>(null) }
//    var cycleTime by rememberSaveable { mutableStateOf("") }
//    var shotLocation by rememberSaveable { mutableStateOf("") }
//    var climbScore by rememberSaveable { mutableStateOf(ClimbScore.NONE) }
//    var humanShot by rememberSaveable { mutableStateOf<Boolean?>(null) }
//    var cargoRP by rememberSaveable { mutableStateOf<Boolean?>(null) }
//    var hangarRP by rememberSaveable { mutableStateOf<Boolean?>(null) }
//
//
//    val inputOk = leftTarmac != null
//            && autoLower != null
//            && autoUpper != null
//            && teleopLower != null
//            && teleopUpper != null
//            && cycleTime.trim().isNotBlank()
//            && shotLocation.trim().isNotBlank()
//            && humanShot != null
//            && climbScore != ClimbScore.NONE
//            && cargoRP != null
//            && hangarRP != null
//
//
////    val competitions = listOf("Beach Blitz 2022")
//    //TODO: get competition list via API call through viewmodel
//
//    var competitions:List<String> by rememberSaveable { mutableStateOf(listOf()) }
//    LaunchedEffect(Unit) {
//        competitions = primaryViewModel.getCompetitions("2022")
//    }
//
//    fun clearForm() {
//        leftTarmac = null
//        autoLower = null
//        autoUpper = null
//        teleopLower = null
//        teleopUpper = null
//        cycleTime = ""
//        shotLocation = ""
//        climbScore = ClimbScore.NONE
//        humanShot = null
//        cargoRP = null
//        hangarRP = null
//    }
//
//    Text(text = "Rapid React Scouting Form", style = MaterialTheme.typography.headlineLarge)
//    Spacer(modifier = Modifier.size(16.dp))
//    BaseScoutingForm(
//        competitions = competitions,
//        onFormSubmit = { _ -> false
//  //        data ->
////            primaryViewModel.submitRapidReact(
////                user = primaryViewModel.user,
////                data = RapidReact(
////                    //no need to check for null safety or validity of inputs here
////                    //this function will only be called if inputOk is true
////                    baseData = data,
////                    leftTarmac = leftTarmac!!,
////                    autoLower = autoLower!!,
////                    autoUpper = autoUpper!!,
////                    teleopLower = teleopLower!!,
////                    teleopUpper = teleopUpper!!,
////                    cycleTime = cycleTime,
////                    shotLocation = shotLocation,
////                    climbScore = climbScore.value,
////                    humanShot = humanShot!!,
////                    cargoRP = cargoRP!!,
////                    hangarRP = hangarRP!!,
////                )
////            )
//        },
//        contentInputsOkay = inputOk,
//        clearContentInputs = ::clearForm
//    ) {
//        //rapid react specific inputs
//        Text(text = "Auto", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.size(16.dp))
//        YesNoSelector(
//            label = "Left Tarmac",
//            value = leftTarmac,
//            setValue = { leftTarmac = it }
//        )
//        Spacer(modifier = Modifier.size(16.dp))
////        LabelledTextBoxSingleLine(
////            label = "Auto Lower Score",
////            text = autoLower,
////            required = true,
////            onValueChange = { autoLower = it },
////            imeAction = ImeAction.Next,
////            keyboardType = KeyboardType.Number
////        )
//        NumberInput(
//            label = "Auto Lower Score",
//            value = autoLower,
//            required = true,
//            onValueChange = { autoLower = it })
//        Spacer(modifier = Modifier.size(16.dp))
//        NumberInput(
//            label = "Auto Upper Score",
//            value = autoUpper,
//            onValueChange = { autoUpper = it },
//            required = true
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        Text(text = "Teleop", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.size(16.dp))
//        NumberInput(
//            label = "Teleop Lower Score",
//            value = teleopLower,
//            onValueChange = { teleopLower = it },
//            required = true
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        NumberInput(
//            label = "Teleop Upper Score",
//            value = teleopUpper,
//            onValueChange = { teleopUpper = it },
//            required = true
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        Text(text = "Robot and Play Style", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.size(16.dp))
//        LabelledTextBoxSingleLine(
//            label = "Cycle Time",
//            required = true,
//            text = cycleTime,
//            onValueChange = { cycleTime = it },
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        LabelledTextBoxSingleLine(
//            label = "Shot Location",
//            required = true,
//            text = shotLocation,
//            onValueChange = { shotLocation = it },
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        Text(text = "Climb Score", style = MaterialTheme.typography.labelLarge)
//        Spacer(modifier = Modifier.size(4.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = climbScore == ClimbScore.ZERO,
//                onClick = { climbScore = ClimbScore.ZERO })
//            Text(text = "0", style = MaterialTheme.typography.labelLarge)
//            Spacer(modifier = Modifier.width(8.dp))
//            RadioButton(
//                selected = climbScore == ClimbScore.FOUR,
//                onClick = { climbScore = ClimbScore.FOUR })
//            Text(text = "4", style = MaterialTheme.typography.labelLarge)
//            Spacer(modifier = Modifier.width(8.dp))
//            RadioButton(
//                selected = climbScore == ClimbScore.SIX,
//                onClick = { climbScore = ClimbScore.SIX })
//            Text(text = "6", style = MaterialTheme.typography.labelLarge)
//            Spacer(modifier = Modifier.width(8.dp))
//            RadioButton(
//                selected = climbScore == ClimbScore.TEN,
//                onClick = { climbScore = ClimbScore.TEN })
//            Text(text = "10", style = MaterialTheme.typography.labelLarge)
//            Spacer(modifier = Modifier.width(8.dp))
//            RadioButton(
//                selected = climbScore == ClimbScore.FIFTEEN,
//                onClick = { climbScore = ClimbScore.FIFTEEN })
//            Text(text = "15", style = MaterialTheme.typography.labelLarge)
//        }
//        Spacer(modifier = Modifier.size(16.dp))
//        YesNoSelector(
//            label = "Human Shot",
//            value = humanShot,
//            setValue = { humanShot = it }
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        YesNoSelector(
//            label = "Cargo Ranking Point",
//            value = cargoRP,
//            setValue = { cargoRP = it }
//        )
//        Spacer(modifier = Modifier.size(16.dp))
//        YesNoSelector(
//            label = "Hangar Ranking Point",
//            value = hangarRP,
//            setValue = { hangarRP = it }
//        )
//    }
//}
//
//enum class ClimbScore(val value: Int) {
//    NONE(-1),
//    ZERO(0),
//    FOUR(4),
//    SIX(6),
//    TEN(10),
//    FIFTEEN(15),
//}