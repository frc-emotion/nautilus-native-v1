package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.screens.scouting.components.RPInput
import org.nautilusapp.nautilus.android.ui.composables.DropDown
import org.nautilusapp.nautilus.android.ui.composables.ErrorAlertDialog
import org.nautilusapp.nautilus.android.ui.composables.LabelledRadioButton
import org.nautilusapp.nautilus.android.ui.composables.LabelledTextBoxSingleLine
import org.nautilusapp.nautilus.android.ui.composables.SuccessAlertDialog
import org.nautilusapp.nautilus.android.ui.composables.TextArea
import org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.ErrorIndicator
import org.nautilusapp.nautilus.android.ui.composables.indicators.LoadingSpinner
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.scouting.GameResult
import org.nautilusapp.nautilus.scouting.tooltips.TooltipInfo


/**
 * @param competitions list of competitions this
 * @param onFormSubmit handle submit for individual game (Rapid React, ChargedUp, etc) and use callback to clear form after
 * @param rpInfo info of ranking points for individual game (Rapid React, ChargedUp, etc). Includes name and information for the tooltip
 * @param contentInputsOkay validate that game-specific fields are filled in and valid before allowing submission
 * @param clearContentInputs clear game-specific fields when form is cleared or submitted
 * @param contents inputs for individual game (Rapid React, ChargedUp, etc)
 */
@Composable
fun BaseScoutingForm(
    competitions: List<String>,
    rpInfo: Pair<RPInfo, RPInfo>,
    onFormSubmit: suspend (baseData: ScoutingSubmissionImpl) -> DataResult<*>,
    contentInputsOkay: Boolean,
    clearContentInputs: () -> Unit,
    contents: @Composable () -> Unit,
) {
    var competition by rememberSaveable {
        mutableStateOf(
            if (competitions.isNotEmpty()) competitions.last() else ""
        )
    } //default to most recent competition
    var teamNumber by rememberSaveable { mutableStateOf("") }
    var matchNumber by rememberSaveable { mutableStateOf("") }
    var defensive by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var finalScore by rememberSaveable { mutableStateOf("") }
    var gameResult by rememberSaveable { mutableStateOf<GameResult?>(null) }
    var penaltyPointsEarned by rememberSaveable { mutableStateOf("") }
    var comments by rememberSaveable { mutableStateOf("") }
    var brokeDown by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var rp1: Boolean? by rememberSaveable { mutableStateOf(null) }
    var rp2: Boolean? by rememberSaveable { mutableStateOf(null) }

    val focusManager = LocalFocusManager.current

    val inputsOkay = competition.trim().isNotBlank()
            && teamNumber.toIntOrNull() != null
            && matchNumber.toIntOrNull() != null
            && defensive != null
            && finalScore.toIntOrNull() != null
            && gameResult != null
            && penaltyPointsEarned.toIntOrNull() != null
            && brokeDown != null
            && contentInputsOkay
            && rp1 != null
            && rp2 != null

    var showInvalidInputDialog by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }
    var showClearFormDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    var isBusy by remember { mutableStateOf(false) }


    fun clearForm() {
        teamNumber = ""
        matchNumber = ""
        defensive = null
        finalScore = ""
        gameResult = null
        penaltyPointsEarned = ""
        comments = ""
        brokeDown = null
        clearContentInputs()
    }


    Text(text = "Match Info", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.size(16.dp))
//    DropDown(label = "Competition", value = competition.ifBlank { "Select: " }) {
//        if (competitions.isNotEmpty()) {
//            competitions.forEachIndexed { index, comp ->
//                DropdownMenuItem(text = { Text(comp) }, onClick = { competition = comp })
//                if (index != competitions.lastIndex) {
//                    Divider()
//                }
//            }
//        } else {
//            DropdownMenuItem(text = { Text("No Competitions Found") }, onClick = { })
//        }
//    }
    DropDown(
        label = "Competition",
        value = competition,
        items = competitions,
        onValueChange = { competition = it })
    Spacer(modifier = Modifier.size(16.dp))
    LabelledTextBoxSingleLine(
        label = "Team Number",
        text = teamNumber,
        required = true,
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next,
        onValueChange = { teamNumber = it })
    Spacer(modifier = Modifier.size(16.dp))
    LabelledTextBoxSingleLine(
        label = "Match Number",
        text = matchNumber,
        required = true,
        keyboardType = KeyboardType.Number,
        onValueChange = { matchNumber = it })
    Spacer(modifier = Modifier.size(16.dp))

    contents()

    Spacer(modifier = Modifier.size(16.dp))
    RPInput(
        info = rpInfo,
        values = Pair(rp1, rp2),
        onFirstChanged = { rp1 = it },
        onSecondChanged = { rp2 = it })
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Robot Information", style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.size(8.dp))
    YesNoSelector(label = "Defense Bot?", value = defensive, setValue = { defensive = it })
    Spacer(modifier = Modifier.size(8.dp))
    YesNoSelector(label = "Robot Broke Down?", value = brokeDown, setValue = { brokeDown = it })
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Scores and Match Results", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.size(8.dp))
    LabelledTextBoxSingleLine(
        label = "Penalty Points Earned",
        text = penaltyPointsEarned,
        required = true,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number,
        onValueChange = { penaltyPointsEarned = it })
    Spacer(modifier = Modifier.size(8.dp))
    LabelledTextBoxSingleLine(
        label = "Final Score",
        text = finalScore,
        required = true,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number,
        onValueChange = { finalScore = it })
    Spacer(modifier = Modifier.size(8.dp))
    Text(text = "Game Result", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        LabelledRadioButton(label = "Win", selected = gameResult == GameResult.WIN) {
            gameResult = GameResult.WIN
        }
        Spacer(modifier = Modifier.size(8.dp))
        LabelledRadioButton(label = "Loss", selected = gameResult == GameResult.LOSS) {
            gameResult = GameResult.LOSS
        }
        Spacer(modifier = Modifier.size(8.dp))
        LabelledRadioButton(label = "Tie", selected = gameResult == GameResult.TIE) {
            gameResult = GameResult.TIE
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    TextArea(label = "Comments/Notes", text = comments, onValueChange = { comments = it })
    Spacer(modifier = Modifier.size(16.dp))
    if (!inputsOkay) {
        ErrorIndicator(text = "Some inputs are invalid/empty. Please check all form fields")
        Spacer(modifier = Modifier.size(16.dp))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = {
                if (inputsOkay) {
                    showSubmitDialog = true
                } else {
                    showInvalidInputDialog = true
                }
            },
            enabled = inputsOkay
        ) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(onClick = {
            showClearFormDialog = true
        }) {
            Text(text = "Clear")
        }
    }
    Spacer(modifier = Modifier.height(64.dp))

    if (showClearFormDialog) {
        AlertDialog(onDismissRequest = {}, dismissButton = {
            TextButton(onClick = {
                showClearFormDialog = false
            }) {
                Text(text = "Cancel")
            }
        }, confirmButton = {
            Button(onClick = {
                focusManager.clearFocus()
                clearForm()
                showClearFormDialog = false
            }) {
                Text(text = "Clear")
            }
        }, title = {
            Text(text = "Clear Form?")
        }, text = {
            Text(text = "Are you sure you want to clear the form?")
        })
    }

    if (showInvalidInputDialog) {
        AlertDialog(onDismissRequest = {}, confirmButton = {
            TextButton(onClick = {
                showInvalidInputDialog = false
            }) {
                Text(text = "OK")
            }
        }, title = {
            Text(text = "Invalid Input")
        }, text = {
            Text(text = "Please check all input fields and try again.")
        })
    }

    val scope = rememberCoroutineScope()

    if (showSubmitDialog && inputsOkay) {
        AlertDialog(
            onDismissRequest = {},
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        isBusy = true
                        val res = onFormSubmit(
                            ScoutingSubmissionImpl(
                                competition = competition,
                                teamNumber = teamNumber.toInt(),
                                matchNumber = matchNumber.toInt(),
                                defensive = defensive!!,
                                score = finalScore.toInt(),
                                won = gameResult == GameResult.WIN,
                                tied = gameResult == GameResult.TIE,
                                penaltyPointsEarned = penaltyPointsEarned.toInt(),
                                brokeDown = brokeDown!!,
                                comments = comments,
                                rankingPoints = calculateRP(rp1!!, rp2!!, gameResult),
                                ranking = RPImpl(
                                    rp1 = rp1!!,
                                    rp2 = rp2!!
                                )
                            )
                        )
                        when (res) {
                            is Result.Success -> {
                                clearForm()
                                isBusy = false
                                showSuccessDialog = true
                            }

                            is Result.Error -> {
                                showErrorDialog = true
                                isBusy = false
                                errorText = res.error.message
                            }
                        }
                    }
                }) {
                    Text(text = "Submit")
                }
            },
            title = {
                Text(text = "Submit Form?")
            },
            text = {
                Text(text = "Are you sure you want to submit the form?")
            }
        )
    }

    LoadingSpinner(isBusy = isBusy)

    SuccessAlertDialog(show = showSuccessDialog) {
        showSuccessDialog = false
    }

    ErrorAlertDialog(show = showErrorDialog, text = errorText) {
        showErrorDialog = false
    }

}

@Preview
@Composable
fun BaseScoutPreview() {
    val competitions = listOf("test", "meow", "woof")
    val info = RPInfo(name = "Meow", tooltipInfo = TooltipInfo("", ""))
    val info2 = info.copy(name = "Meow2")
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            BaseScoutingForm(
                competitions = competitions,
                rpInfo = Pair(info, info2),
                onFormSubmit = { Result.Success(Unit) },
                contentInputsOkay = true,
                clearContentInputs = {},
                contents = {
                    Text(text = "Test")
                }
            )
        }
    }
}

/**
 * Calculates ranking points based on game result and ranking point conditions
 * @returns number of ranking points earned in range 0..4
 */
fun calculateRP(rp1: Boolean, rp2: Boolean, gameResult: GameResult?): Int {
    var out = 0
    if (rp1) {
        out += 1
    }
    if (rp2) {
        out += 1
    }
    when (gameResult) {
        GameResult.WIN -> out += 2
        GameResult.TIE -> out++
        else -> Unit
    }
    return out
}