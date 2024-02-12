package org.nautilusapp.nautilus.android.viewmodels

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.attendance.MeetingLog
import org.nautilusapp.nautilus.userauth.Subteam

class MainViewModel(
    private val dataHandler: DataHandler,
    private val connectivityManager: ConnectivityManager?,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    var user by mutableStateOf(Result.unwrapOrNull(dataHandler.users.loadLoggedIn()))
        private set

    fun theme(): ColorTheme {
        val themeStr = sharedPref.getString("theme", null)
        return try {
            ColorTheme.valueOf("$themeStr")
        } catch (e: IllegalArgumentException) {
            ColorTheme.MATERIAL3
        }
    }

    var theme by mutableStateOf(theme())
        private set

    suspend fun setTheme(theme: ColorTheme) {
        delay(600L) //debounce
        with(sharedPref.edit()) {
            putString("theme", theme.name)
            apply()
        }
        this.theme = theme
    }

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

    suspend fun syncMe() {
        dataHandler.users.refreshLoggedIn().let {
            when(it) {
                is Result.Success -> user = it.data
                is Result.Error -> println(it.error)
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