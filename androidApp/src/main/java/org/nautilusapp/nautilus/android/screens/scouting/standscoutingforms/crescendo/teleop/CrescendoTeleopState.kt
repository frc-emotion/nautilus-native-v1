package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.teleop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoTeleop

class CrescendoTeleopState {
    var amp: Int? by mutableStateOf(null)
    var speakerUnamped: Int? by mutableStateOf(null)
    var speakerAmped: Int? by mutableStateOf(null)

    fun clear() {
        amp = null
        speakerUnamped = null
        speakerAmped = null
    }

    val data: CrescendoTeleop?
        get() {
            val amp = this.amp ?: return null
            val speakerUnamped = this.speakerUnamped ?: return null
            val speakerAmped = this.speakerAmped ?: return null
            return CrescendoTeleop(
                ampNotes = amp,
                speakerUnamped = speakerUnamped,
                speakerAmped = speakerAmped
            )
        }
    val isValid: Boolean
        get() = this.data != null
}