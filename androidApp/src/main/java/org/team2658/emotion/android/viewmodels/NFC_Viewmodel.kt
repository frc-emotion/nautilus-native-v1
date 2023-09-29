package org.team2658.emotion.android.viewmodels

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.nio.charset.Charset

class NFC_Viewmodel: ViewModel() {
    var nfcTag: Tag? by mutableStateOf(null)
        private set

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

    fun writeToTag(text: String) {
        if (this.nfcTag == null) return
        val ndef = NdefMessage(NdefRecord.createMime("text/plain", text.toByteArray(Charset.forName("US-ASCII"))))
//        MifareUltralight.get(this.nfcTag)?.use {
//            it.connect()
//            it.writePage(4, text.toByteArray(Charset.forName("US-ASCII")))
//            println("Trying to write $text")
//        }
        Ndef.get(this.nfcTag)?.use {
            it.connect()
            it.writeNdefMessage(ndef)
            println("Trying to write $text")
        }
    }
}