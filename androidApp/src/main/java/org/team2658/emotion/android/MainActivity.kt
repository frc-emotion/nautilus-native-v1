package org.team2658.emotion.android

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.screens.settings.SettingsScreen
import org.team2658.emotion.android.ui.navigation.LoggedInNavigator
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.userauth.AuthState
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    private val ktorClient = EmotionClient()
    private val nfcViewmodel by viewModels<NFCViewmodel>()
    private val scoutingDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            org.team2658.emotion.android.room.dbs.ScoutingDB::class.java,
            "scouting.db"
        ).fallbackToDestructiveMigration().build()
    }

    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")

        val workRequest = PeriodicWorkRequestBuilder<SyncTrigger>(15, TimeUnit.MINUTES)
            .build()

        handleNFCIntent(intent)

        val connectivityManager = this.applicationContext?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        workManager = WorkManager.getInstance(this.applicationContext)

        setContent {
            val sharedPrefs: SharedPreferences = LocalContext.current.getSharedPreferences("org.team2658.emotion.android", MODE_PRIVATE)
            val primaryViewModel = viewModel<PrimaryViewModel>(
                factory = object: ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                    ):T {
                        return PrimaryViewModel(ktorClient, sharedPrefs, scoutingDB, connectivityManager) as T
                    }
                }
            )

            workManager.enqueueUniquePeriodicWork(
                "sync",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
            workManager.getWorkInfosForUniqueWorkLiveData("sync").observeForever {
                println("BACKGROUND SYNC TRIGGERED")
                primaryViewModel.sync()
            }

            MainTheme {
                if (primaryViewModel.authState == AuthState.LOGGED_IN) {
                    LoggedInNavigator(primaryViewModel, ktorClient, nfcViewmodel)
                } else {
                    Scaffold { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            SettingsScreen(primaryViewModel)
                        }
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ktorClient.close()
        scoutingDB.close()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("New Intent")
       handleNFCIntent(intent)
    }

    private fun handleNFCIntent(intent: Intent?) {
        if(intent?.action == NfcAdapter.ACTION_TAG_DISCOVERED || intent?.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            when {
                SDK_INT >= 33 -> {
                    this.nfcViewmodel.setTag(
                        intent.getParcelableExtra(
                            NfcAdapter.EXTRA_TAG,
                            Tag::class.java
                        )
                    )
                    println("NFC Tag Detected")
                }

                else -> {
                    @Suppress("DEPRECATION")
                    this.nfcViewmodel.setTag(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG))
                }
            }
        }
        if(intent?.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            when {
                SDK_INT >= 33 -> {
                        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES, NdefMessage::class.java)?.also { raw ->
                           this.nfcViewmodel.setNdef( raw.map{ it as NdefMessage } )
                        }

                    println("NFC Tag Detected")
                }

                else -> {
                    @Suppress("DEPRECATION")
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { raw->
                        this.nfcViewmodel.setNdef( raw.map { it as NdefMessage } )
                    }
                }
            }
        }
    }
}