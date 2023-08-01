package org.team2658.emotion.android.screens.scouting.standscoutingforms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.team2658.emotion.android.ui.composables.DropDown
import org.team2658.emotion.android.ui.composables.ErrorAlertDialog
import org.team2658.emotion.android.ui.composables.LabelledTextBoxSingleLine
import org.team2658.emotion.android.ui.composables.SuccessAlertDialog
import org.team2658.emotion.android.ui.composables.TextArea
import org.team2658.emotion.android.ui.composables.YesNoSelector
import org.team2658.emotion.scouting.GameResult
import org.team2658.emotion.scouting.scoutingdata.ScoutingData

@Composable
fun BaseScoutingForm(
    competitions: List<String>,
    onFormSubmit: suspend (baseData: ScoutingData) -> Int,
    //handle submit for individual game (Rapid React, ChargedUp, etc) and use callback to clear form after
    contentInputsOkay: Boolean,
    //make sure game-specific required fields are filled in and valid
    clearContentInputs: () -> Unit,
    //callback to clear game-specific fields
    contents: @Composable () -> Unit,
    //put in inputs for game-specific fields in here
) {
    var competition by rememberSaveable { mutableStateOf(competitions.last()) } //default to most recent competition
    var teamNumber by rememberSaveable { mutableStateOf("") }
    var matchNumber by rememberSaveable { mutableStateOf("") }
    var defensive by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var finalScore by rememberSaveable { mutableStateOf("") }
    var gameResult by rememberSaveable { mutableStateOf<GameResult?>(null) }
    var penaltyPointsEarned by rememberSaveable { mutableStateOf("") }
    var comments by rememberSaveable { mutableStateOf("") }
    var brokeDown by rememberSaveable { mutableStateOf<Boolean?>(null) }

    val inputsOkay = competition.trim().isNotBlank()
            && teamNumber.toIntOrNull() != null
            && matchNumber.toIntOrNull() != null
            && defensive != null
            && finalScore.toIntOrNull() != null
            && gameResult != null
            && penaltyPointsEarned.toIntOrNull() != null
            && brokeDown != null
            && contentInputsOkay

    var showInvalidInputDialog by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }
    var showClearFormDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    fun clearForm() {
        competition = competitions[0]
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
    DropDown(label = "Competition", value = competition) {
        competitions.forEachIndexed { index, comp ->
            DropdownMenuItem(text = { Text(comp) }, onClick = { competition = comp })
            if (index != competitions.lastIndex) {
                Divider()
            }
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    LabelledTextBoxSingleLine(
        label = "Team Number",
        text = teamNumber,
        required = true,
        keyboardType = KeyboardType.Number,
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
    YesNoSelector(label = "Robot Was Defensive?", value = defensive, setValue = { defensive = it })
    Spacer(modifier = Modifier.size(16.dp))
    YesNoSelector(label = "Robot Broke Down?", value = brokeDown, setValue = { brokeDown = it })
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Scores and Match Results", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.size(16.dp))
    LabelledTextBoxSingleLine(
        label = "Penalty Points Earned",
        text = penaltyPointsEarned,
        required = true,
        keyboardType = KeyboardType.Number,
        onValueChange = { penaltyPointsEarned = it })
    Spacer(modifier = Modifier.size(16.dp))
    LabelledTextBoxSingleLine(
        label = "Final Score",
        text = finalScore,
        required = true,
        keyboardType = KeyboardType.Number,
        onValueChange = { finalScore = it })
    Spacer(modifier = Modifier.size(16.dp))
    Text(text = "Game Result", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = gameResult == GameResult.WIN,
            onClick = { gameResult = GameResult.WIN })
        Text(text = "Win", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(8.dp))
        RadioButton(
            selected = gameResult == GameResult.LOSS,
            onClick = { gameResult = GameResult.LOSS })
        Text(text = "Loss", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(8.dp))
        RadioButton(
            selected = gameResult == GameResult.TIE,
            onClick = { gameResult = GameResult.TIE })
        Text(text = "Tie", style = MaterialTheme.typography.labelLarge)
    }
    Spacer(modifier = Modifier.size(16.dp))
    TextArea(label = "Comments/Notes", text = comments, onValueChange = { comments = it })
    Spacer(modifier = Modifier.size(16.dp))
    if (!inputsOkay) {
        Text(
            text = "Some inputs are invalid/empty. Please check all form fields",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            if (inputsOkay) {
                showSubmitDialog = true
            } else {
                showInvalidInputDialog = true
            }
        }) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(onClick = {
            showClearFormDialog = true
        }) {
            Text(text = "Clear")
        }
    }

    if (showClearFormDialog) {
        AlertDialog(onDismissRequest = {}, dismissButton = {
            TextButton(onClick = {
                showClearFormDialog = false
            }) {
                Text(text = "Cancel")
            }
        }, confirmButton = {
            Button(onClick = {
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
                    runBlocking {
                        val code = onFormSubmit(
                            ScoutingData(
                                competition = competition,
                                teamNumber = teamNumber.toInt(),
                                matchNumber = matchNumber.toInt(),
                                defensive = defensive!!,
                                finalScore = finalScore.toInt(),
                                gameResult = gameResult!!,
                                penaltyPointsEarned = penaltyPointsEarned.toInt(),
                                brokeDown = brokeDown!!,
                                comments = comments
                            )
                        )
                        if (code == 0) {
                            clearForm()
                            showSuccessDialog = true
                        } else {
                            showErrorDialog = true
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

    SuccessAlertDialog(show = showSuccessDialog) {
        showSuccessDialog = false
    }

    ErrorAlertDialog(show = showErrorDialog) {
        showErrorDialog = false
    }

}