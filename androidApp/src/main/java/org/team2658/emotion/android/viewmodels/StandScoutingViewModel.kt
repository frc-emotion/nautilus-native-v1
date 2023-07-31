package org.team2658.emotion.android.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.team2658.emotion.scouting.scoutingdata.RapidReact
import org.team2658.emotion.userauth.User

class StandScoutingViewModel : ViewModel() {
    suspend fun submitRapidReact(data: RapidReact, user: User?): Int {
        //TODO
        delay(200L)
        //return 0 if successful, any other number is an error
        return 0
    }
}