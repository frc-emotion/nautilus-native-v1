package org.team2658.emotion.android

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ACTION_NDEF_DISCOVERED
import android.nfc.NfcAdapter.ACTION_TAG_DISCOVERED
import android.nfc.NfcAdapter.ACTION_TECH_DISCOVERED
import android.nfc.NfcAdapter.getDefaultAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
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
    private val attendanceDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            org.team2658.emotion.android.room.dbs.AttendanceDB::class.java,
            "attendance.db"
        ).fallbackToDestructiveMigration().build()
    }
    private lateinit var workManager: WorkManager
    private lateinit var pendingIntent: PendingIntent
    private val intentFilters = arrayOf(
//        IntentFilter(ACTION_NDEF_DISCOVERED).apply {
//            addDataType("*/*")
//        },
//        IntentFilter(ACTION_TAG_DISCOVERED),
        IntentFilter(ACTION_TECH_DISCOVERED)
    )
    private val techLists = arrayOf(
        arrayOf(Ndef::class.java.name)
    )
    private var adapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("onCreate")


        val workRequest = PeriodicWorkRequestBuilder<SyncTrigger>(15, TimeUnit.MINUTES)
            .build()

        adapter = getDefaultAdapter(this)

        handleNFCIntent(intent)
        val tent = intent.setAction(ACTION_TECH_DISCOVERED)
        pendingIntent = if (SDK_INT >= 34) {
            PendingIntent.getActivity(
                this,
                0,
                tent,
                PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0,
                tent,
                PendingIntent.FLAG_MUTABLE
            )
        }

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
                        return PrimaryViewModel(ktorClient, sharedPrefs, scoutingDB, connectivityManager, attendanceDB, nfcViewmodel::clearNFC) as T
                    }
                }
            )

            primaryViewModel.sync()

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
        attendanceDB.close()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("onNewIntent")
        println("pending intent: $pendingIntent")
        handleNFCIntent(intent)
    }

    override fun onPause() {
        super.onPause()
        adapter?.disableForegroundDispatch(this)
        println("pending intent: $pendingIntent")
        println("onPause")
        adapter = null
    }

    override fun onResume() {
        super.onResume()
        if(adapter == null) {
            adapter = getDefaultAdapter(this)
        }
        println("onResume")
        println("pending intent: $pendingIntent")
        adapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists)
    }

    private fun handleNFCIntent(intent: Intent?) {
        println("Handling NFC Intent: ${intent?.action}")
        if(intent?.action == ACTION_TECH_DISCOVERED || intent?.action == ACTION_NDEF_DISCOVERED || intent?.action == ACTION_TAG_DISCOVERED) {
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
        if(intent?.action == ACTION_NDEF_DISCOVERED) {
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