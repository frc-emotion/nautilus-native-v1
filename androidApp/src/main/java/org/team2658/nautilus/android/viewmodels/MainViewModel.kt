package org.team2658.nautilus.android.viewmodels

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.team2658.nautilus.DataHandler
import org.team2658.nautilus.Result
import org.team2658.nautilus.attendance.MeetingLog
import org.team2658.nautilus.userauth.Subteam

class MainViewModel(
    private val dataHandler: DataHandler,
    private val connectivityManager: ConnectivityManager?
) : ViewModel() {

    var user by mutableStateOf(Result.unwrapOrNull(dataHandler.users.loadLoggedIn()))
        private set


    fun login(username: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            when(val result = dataHandler.users.login(username, password)) {
                is Result.Success -> user = result.data
                is Result.Error -> onError(result.error)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataHandler.users.logout()
            user = null
        }
    }

    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        subteam: Subteam,
        phone: String,
        grade: Int,
        errorCallback: (String) -> Unit
    ) {
        viewModelScope.launch {
            val res = dataHandler.users.register(
                username,
                password,
                email,
                firstName,
                lastName,
                subteam,
                phone,
                grade
            )
            when(res) {
                is Result.Success -> user = res.data
                is Result.Error -> errorCallback(res.error)
            }
        }
    }

    fun attendMeeting(meetingInfo: MeetingLog, onError: (String) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val res = dataHandler.attendance.attend(
                meetingId = meetingInfo.meetingId,
                verifiedBy = meetingInfo.verifiedBy,
                time = System.currentTimeMillis(),
            )
            when(res) {
                is Result.Success -> onSuccess().also { user = res.data }
                is Result.Error -> onError(res.error)
            }
        }
    }

    fun deleteMe(password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            dataHandler.getNetworkClient().users.login(
                user?.username ?: "",
                password
            ).let {
                    when(it) {
                        is Result.Success -> {
                            val res = dataHandler.users.deleteMe()
                            when(res) {
                                is Result.Success -> onComplete(true, null).also { user = null }
                                is Result.Error -> onComplete(false, res.error)
                            }
                        }
                        is Result.Error -> {}
                    }
            }
        }
    }

    fun sync() {
        viewModelScope.launch {
            dataHandler.sync().let {
                when(val me = it.user.myUser) {
                    is Result.Success -> user = me.data
                    is Result.Error -> println(me.error)
                }
            }
        }
    }

    suspend fun coroutineSync() {
        dataHandler.sync().let {
            when(val me = it.user.myUser) {
                is Result.Success -> user = me.data
                is Result.Error -> println(me.error)
            }
        }
    }

    fun sync(onProgressChanged: (busy: Boolean, success: Boolean?) -> Unit) {
        onProgressChanged(true, null)
        viewModelScope.launch {
            println(connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            if(connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) != true) {
                return@launch onProgressChanged(false, false)
            }
            dataHandler.sync().let {
                when(val me = it.user.myUser) {
                    is Result.Success -> user = me.data
                    is Result.Error -> {
                        println(me.error)
                        return@launch onProgressChanged(false, false)
                    }
                }
            }
            onProgressChanged(false, true)
        }
    }

    fun getQueueLength() = dataHandler.getQueueLength()

    fun getDataHandler() = this.dataHandler

}