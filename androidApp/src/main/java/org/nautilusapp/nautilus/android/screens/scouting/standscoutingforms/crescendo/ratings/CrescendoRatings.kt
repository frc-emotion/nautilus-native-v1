package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.ratings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoHuman

class CrescendoRatings {
    var defenseRating by mutableStateOf<Double?>(null)
    var humanRating by mutableStateOf<Double?>(null)
    var coopertition by mutableStateOf<Boolean?>(null)
    var rating by mutableStateOf<Double?>(null)
    var humanSource by mutableStateOf<Boolean?>(null)

    fun clear() {
        defenseRating = null
        humanRating = null
        coopertition = null
        rating = null
        humanSource = null
    }

    val human: CrescendoHuman?
        get() {
            val rating = this.humanRating ?: return null
            val source = this.humanSource ?: return null
            return CrescendoHuman(rating = rating, source = source)
        }

    val isValid: Boolean
        get() = defenseRating != null && humanRating != null && coopertition != null && rating != null && humanSource != null && human != null
}