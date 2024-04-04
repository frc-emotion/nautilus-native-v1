package org.nautilusapp.nautilus.android.screens.scouting.standscoutingforms.crescendo.auto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto

class CrescendoAutoState {
    var attempted: Int? by mutableStateOf(null)
    var scored: Int? by mutableStateOf(null)
    var leave: Boolean? by mutableStateOf(null)

    fun clear() {
        attempted = null
        scored = null
        leave = null
    }

    val data: CrescendoAuto?
        get() {
            val attempted = this.attempted ?: return null
            val scored = this.scored ?: return null
            val leave = this.leave ?: return null
            return CrescendoAuto(leave = leave, attempted = attempted, scored = scored)
        }

    val isValid: Boolean
        get() = data != null && attempted!! >= scored!!
}