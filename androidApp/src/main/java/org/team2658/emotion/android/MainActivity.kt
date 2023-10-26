package org.team2658.emotion.android

import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.runBlocking
import org.team2658.apikt.EmotionClient
import org.team2658.emotion.android.screens.settings.SettingsScreen
import org.team2658.emotion.android.ui.composables.Screen
import org.team2658.emotion.android.ui.navigation.LoggedInNavigator
import org.team2658.emotion.android.viewmodels.NFCViewmodel
import org.team2658.emotion.android.viewmodels.PrimaryViewModel
import org.team2658.emotion.userauth.AuthState

class MainActivity : ComponentActivity() {
    private val ktorClient = EmotionClient()
    private val nfcViewmodel by viewModels<NFCViewmodel>()
    private var init = false
//    private val sharedPrefs = getPreferences(MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init = true
        println("onCreate")
        handleNFCIntent(intent)
        setContent {
            val sharedPrefs: SharedPreferences = LocalContext.current.getSharedPreferences("org.team2658.emotion.android", MODE_PRIVATE)
            val primaryViewModel = viewModel<PrimaryViewModel>(
                factory = object: ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                    ):T {
                        return PrimaryViewModel(ktorClient, sharedPrefs) as T
                    }
                }
            )
            if(init) {
                runBlocking {
                    primaryViewModel.updateMe()
                }
                init = false
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
//                Screen {
//                    if(this.nfcViewmodel.nfcTag != null) {
//                        Text("NFC Tag Detected")
//                        Text(this.nfcViewmodel.nfcTag.toString())
//                       Button(onClick = {
//                            this.nfcViewmodel.writeToTag("Shravan is sexy")
//                       }) {
//                           Text("Write to tag")
//                       }
//                    }  else {
//                            Text("No Tag")
//                    }
//                    if(this.nfcViewmodel.ndefMessages != null) {
//                    Text(this.nfcViewmodel.ndefMessages.toString())
//                        Text(this.nfcViewmodel.getNdefPayload().toString())
//                    }
//                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ktorClient.close()
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
                        this.nfcViewmodel.setNdef( raw.map {it as NdefMessage})
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MainTheme {
        GreetingView("Hello, Android!")
    }
}
