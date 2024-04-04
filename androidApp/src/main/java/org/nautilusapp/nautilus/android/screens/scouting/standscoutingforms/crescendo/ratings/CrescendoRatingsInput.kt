package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.ratings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.LabelledRadioButton
import org.nautilusapp.nautilus.android.ui.composables.StarRating
import org.nautilusapp.nautilus.android.ui.composables.YesNoSelector
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.MinimalWarning
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun CrescendoRatingsInput(state: CrescendoRatings) {
    Row {
        Text("Ratings", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(8.dp))
        if (!state.isValid) {
            MinimalWarning("Empty Fields")
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Text(text = "Defense Rating (if applicable)", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    StarRating(stars = 5, fraction = state.defenseRating ?: -1.0) {
        state.defenseRating = it
    }
    Spacer(modifier = Modifier.size(8.dp))

    Text(text = "Robot Rating", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    StarRating(stars = 5, fraction = state.rating ?: -1.0) {
        state.rating = it
    }
    Spacer(modifier = Modifier.size(8.dp))
    Text(text = "Human Player Position", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    Row {
        LabelledRadioButton(label = "Source", selected = state.humanSource == true) {
            state.humanSource = true
        }
        Spacer(modifier = Modifier.size(8.dp))
        LabelledRadioButton(label = "Amp", selected = state.humanSource == false) {
            state.humanSource = false
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    Text(text = "Human Player Rating", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.size(4.dp))
    StarRating(stars = 5, fraction = state.humanRating ?: -1.0) {
        state.humanRating = it
    }
    Spacer(modifier = Modifier.size(8.dp))
    YesNoSelector(label = "Coopertition", value = state.coopertition) {
        state.coopertition = it
    }
}

@Preview
@Composable
private fun RatePrev() {
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            val state = remember {
                CrescendoRatings()
            }
            CrescendoRatingsInput(state)
        }
    }
}