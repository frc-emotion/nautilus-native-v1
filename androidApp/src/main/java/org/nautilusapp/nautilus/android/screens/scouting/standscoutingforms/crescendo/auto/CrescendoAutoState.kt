package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto

class CrescendoAutoState {
    var amp: Int? by mutableStateOf(null)
    var speaker: Int? by mutableStateOf(null)
    var leave: Boolean? by mutableStateOf(null)

    fun clear() {
        amp = null
        speaker = null
        leave = null
    }

    val data: CrescendoAuto?
        get() {
            val amp = this.amp ?: return null
            val speaker = this.speaker ?: return null
            val leave = this.leave ?: return null
            return CrescendoAuto(leave, amp, speaker)
        }

    val isValid: Boolean
        get() = data != null
}