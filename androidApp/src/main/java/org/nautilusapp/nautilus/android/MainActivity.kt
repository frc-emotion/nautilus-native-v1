package org.nautilusapp.nautilus.android

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.android.screens.UpdateNeededScreen.UpdateNeededScreen
import org.nautilusapp.nautilus.android.screens.settings.SettingsScreen
import org.nautilusapp.nautilus.android.ui.composables.indicators.LoadingSpinner
import org.nautilusapp.nautilus.android.ui.navigation.LoggedInNavigator
import org.nautilusapp.nautilus.android.viewmodels.MainViewModel
import org.nautilusapp.nautilus.android.viewmodels.NFCViewmodel
import org.nautilusapp.nautilus.userauth.AuthState
import org.nautilusapp.nautilus.userauth.authState
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    private val _busy = MutableStateFlow(true)
    private val nfcViewmodel by viewModels<NFCViewmodel>()
    private lateinit var workManager: WorkManager
    private lateinit var pendingIntent: PendingIntent
    private val intentFilters = arrayOf(
        IntentFilter(ACTION_TECH_DISCOVERED)
    )
    private val techLists = arrayOf(
        arrayOf(Ndef::class.java.name)
    )
    private var adapter: NfcAdapter? = null

    private lateinit var dataHandler: DataHandler

    private val updateIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://play.google.com/store/apps/details?id=org.nautilusapp.nautilus"
        )
        setPackage("com.android.vending")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")

        val sharedPref =
            this.getSharedPreferences("org.nautilusapp.nautilus", MODE_PRIVATE)

        val rootURL = sharedPref.getString(SharedPrefKeys.URL, null)

        dataHandler = DataHandler(
            databaseDriverFactory =
            org.nautilusapp.localstorage.AndroidDatabaseDriver(this),
            getToken = {
                return@DataHandler sharedPref.getString(SharedPrefKeys.TOKEN, null)
            },
            routeBase = rootURL ?: ""
        ) {
            with(sharedPref.edit()) {
                putString(SharedPrefKeys.TOKEN, it)
                apply()
            }
        }


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

        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        workManager = WorkManager.getInstance(this.applicationContext)


        setContent {
            val isBusy by _busy.collectAsState()
            var manifestOk: Boolean? by remember { mutableStateOf(null) }

            LaunchedEffect(Unit) {
                manifestOk = dataHandler.manifestOk()
            }

            LaunchedEffect(manifestOk) {
                manifestOk?.let {
                    _busy.value = false
                }
            }

            val primaryViewModel = viewModel<MainViewModel>(
                factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                    ): T {
                        return MainViewModel(dataHandler, connectivityManager, sharedPref) as T
                    }
                }
            )

            LaunchedEffect(primaryViewModel.user) {
                if (primaryViewModel.user == null) {
                    nfcViewmodel.setTag(null)
                    nfcViewmodel.setNdef(null)
                }
            }

            workManager.enqueueUniquePeriodicWork(
                "sync",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
            workManager.getWorkInfosForUniqueWorkLiveData("sync").observeForever {
                println("BACKGROUND SYNC TRIGGERED")
                primaryViewModel.getDataHandler().bgSync()
            }

            val snack = remember { SnackbarHostState() }

            MainTheme(
                preference =
                primaryViewModel.theme
            ) {
                LoadingSpinner(isBusy)
                if (manifestOk == true) {
                    if (authState(primaryViewModel.user) == AuthState.LOGGED_IN && rootURL?.isNotBlank() == true) {
                        LoggedInNavigator(primaryViewModel, dataHandler, nfcViewmodel, snack)
                    } else {
                        if (rootURL?.isBlank() == true) {
                            dataHandler.users.logout()
                        }
                        Scaffold(
                            snackbarHost = {
                                SnackbarHost(snack) {
                                    Snackbar(
                                        snackbarData = it,
                                        containerColor = cardColor(),
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                        ) { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                SettingsScreen(primaryViewModel, snack = snack)
                            }
                        }
                    }
                } else {
                    Scaffold { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            UpdateNeededScreen(manifestOk) {
                                startActivity(updateIntent)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataHandler.getNetworkClient().close()
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
        if (adapter == null) {
            adapter = getDefaultAdapter(this)
        }
        println("onResume")
        println("pending intent: $pendingIntent")
        adapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists)
    }

    private fun handleNFCIntent(intent: Intent?) {
        println("Handling Intent: ${intent?.action}")
        if (intent?.action == ACTION_TECH_DISCOVERED || intent?.action == ACTION_NDEF_DISCOVERED || intent?.action == ACTION_TAG_DISCOVERED) {
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
        if (intent?.action == ACTION_NDEF_DISCOVERED) {
            when {
                SDK_INT >= 33 -> {
                    intent.getParcelableArrayExtra(
                        NfcAdapter.EXTRA_NDEF_MESSAGES,
                        NdefMessage::class.java
                    )?.also { raw ->
                        this.nfcViewmodel.setNdef(raw.map { it as NdefMessage })
                    }

                    println("NFC Tag Detected")
                }

                else -> {
                    @Suppress("DEPRECATION")
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { raw ->
                        this.nfcViewmodel.setNdef(raw.map { it as NdefMessage })
                    }
                }
            }
        }
    }
}