package com.projetointegrador.pi_application.core.utils.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun Context.copyToClipboard(text: String) {
    val myClipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val myClip = ClipData.newPlainText(text, text)
    myClipboard.setPrimaryClip(myClip)
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}