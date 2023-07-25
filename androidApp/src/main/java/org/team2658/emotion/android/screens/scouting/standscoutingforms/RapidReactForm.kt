package org.team2658.emotion.android.screens.scouting.standscoutingforms

import androidx.compose.runtime.Composable

@Composable
fun RapidReactForm() {
    val inputOk = true //TODO
    val competitions = listOf("Competition 1", "Competition 2", "Competition 3", "Competition 4")
    //TODO: get competition list via API call through viewmodel
    BaseScoutingForm(
        competitions = competitions,
        onFormSubmit = { data, clear ->
            //TODO
            clear()
        },
        contentInputsOkay = inputOk
    ) {
        //TODO
    }
}