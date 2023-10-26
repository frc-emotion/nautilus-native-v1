package org.team2658.emotion.android.viewmodels

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.nio.charset.Charset

class NFCViewmodel: ViewModel() {
    private var nfcTag: Tag? by mutableStateOf(null)

    fun tagConnected(): Boolean {
        var out = false
        this.nfcTag?.let {
            try {
                Ndef.get(it)?.use {tag ->
                    tag.connect()
                    out = tag.isConnected
                }
            }catch(e: IOException) {
                out = false
            }
        }
        if(!out) this.setTag(null)
        return out
    }

    var ndefMessages: List<NdefMessage>? by mutableStateOf(null)
        private set

    fun setTag(tag: Tag?) {
        this.nfcTag = tag
    }

    fun setNdef(messages: List<NdefMessage>?) {
       this.ndefMessages = messages
    }

    fun getNdefPayload(): String? {
        if (this.ndefMessages == null) return null
        return String(this.ndefMessages!![0].records[0].payload, Charset.forName("US-ASCII"))
    }

    fun writeToTag(text: String):Boolean {
        if (this.nfcTag == null) return false

        val ndef = NdefMessage(
            NdefRecord.createMime(
                "text/plain",
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
}