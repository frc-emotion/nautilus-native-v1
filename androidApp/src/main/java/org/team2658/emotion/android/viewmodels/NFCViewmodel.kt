package org.team2658.emotion.android.viewmodels

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.nio.charset.Charset

class NFCViewmodel: ViewModel() {
    private var nfcTag: Tag? by mutableStateOf(null)

    private suspend fun tagConnected(): Boolean {
        return withContext(Dispatchers.IO) {
            var out = false
            nfcTag?.let {
                try {
                    Ndef.get(it)?.use { tag ->
                        out = try {
                            tag.connect()
                            try {
                                tag.isConnected
                            } catch (e: Exception) {
                                setTag(null)
                                false
                            }
                        } catch (e: Exception) {
                            setTag(null)
                            false
                        }
                    }
                } catch (e: Exception) {
                    setTag(null)
                    out = false
                }
                if (!out) setTag(null)
            }
            return@withContext out
        }
    }

    val tagConnectionFlow = flow {
        while(true) {
            emit(tagConnected())
            delay(500L)
        }
    }

    var ndefMessages: List<NdefMessage>? by mutableStateOf(null)
        private set


    fun setNdef(messages: List<NdefMessage>?) {
       this.ndefMessages = messages
    }

    fun setTag(tag: Tag?) {
        this.nfcTag = tag
        tag?.let {
            try {
                Ndef.get(it)?.use { ndef ->
                    try {
                        ndef.connect()
                        ndefMessages = listOf(ndef.ndefMessage)
                    } catch (e: Exception) {
                        this.ndefMessages = null
                    }
                }
            }catch (e: Exception) {
                this.nfcTag = null
            }
        }
        println("payload: ${ndefMessages?.get(0)?.records?.get(0)?.payload?.toString(Charset.forName("US-ASCII"))}")
        println("ndefMessages: $ndefMessages")
    }

    fun writeToTag(text: String):Boolean {
        if (this.nfcTag == null) return false

        val ndef = NdefMessage(
            NdefRecord.createMime(
                "application/emotion",
                text.toByteArray(Charset.forName("US-ASCII"))
            )
        )
        Ndef.get(this.nfcTag)?.use {
                it.connect()
                it.writeNdefMessage(ndef)
                println("Trying to write $text")
                setTag(null)
                return true
        }
        return false
    }

    fun clearNFC() {
        setTag(null)
        setNdef(null)
    }
}