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

    var user by mutableStateOf(dataHandler.users.loadLoggedIn())
        private set


    fun login(username: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            user = dataHandler.users.login(username, password, onError)
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
            user = dataHandler.users.register(
                username,
                password,
                email,
                firstName,
                lastName,
                subteam,
                phone,
                grade,
                errorCallback
            )
        }
    }

    fun attendMeeting(meetingInfo: MeetingLog, onError: (String) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch {
            dataHandler.attendance.attend(
                meetingId = meetingInfo.meetingId,
                verifiedBy = meetingInfo.verifiedBy ?: "unknown",
                time = System.currentTimeMillis(),
                onError = onError,
                onSuccess = {
                    onSuccess()
                    user = dataHandler.users.loadLoggedIn()
                }
            )
        }
    }

    fun deleteMe(password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            dataHandler.getNetworkClient().users.login(user?.username ?: "",
                password,
                errorCallback = { str -> onComplete(false, str) }).let {
                    when(it) {
                        is Result.Success -> {
                            dataHandler.users.deleteMe(onComplete)
                            user = dataHandler.users.loadLoggedIn()
                        }
                        is Result.Error -> {}
                    }
            }
        }
    }

    fun sync() {
        viewModelScope.launch {
            dataHandler.sync()
            user = dataHandler.users.loadLoggedIn()
        }
    }

    suspend fun coroutineSync() {
        dataHandler.syncCoroutine()
        user = dataHandler.users.loadLoggedIn()
    }

    fun sync(onProgressChanged: (busy: Boolean, success: Boolean?) -> Unit) {
        onProgressChanged(true, null)
        viewModelScope.launch {
            println(connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            if(connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) != true) {
                println("meow meow meow")
                return@launch onProgressChanged(false, false)
            }
            dataHandler.sync()
            user = dataHandler.users.loadLoggedIn()
            onProgressChanged(false, true)
        }
    }

    fun getQueueLength() = dataHandler.getQueueLength()

    fun getDataHandler() = this.dataHandler

}