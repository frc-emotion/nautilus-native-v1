package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.endgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStage
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStageState

class CrescendoEndgame {
    var parkstate: CrescendoStageState? by mutableStateOf(null)
    var harmony: Int? by mutableStateOf(null)
    var trap: Int? by mutableStateOf(null)

    fun clear() {
        parkstate = null
        harmony = null
        trap = null
    }

    val data: CrescendoStage?
        get() {
            val state = this.parkstate ?: return null
            val harmony = this.harmony ?: return null
            val trap = this.trap ?: return null
            return CrescendoStage(state, harmony, trap)
        }

    val isValid: Boolean
        get() = this.data != null && harmony in (0..2)
}