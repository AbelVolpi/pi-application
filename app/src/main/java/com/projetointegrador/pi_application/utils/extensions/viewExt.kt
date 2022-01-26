package com.projetointegrador.pi_application.utils.extensions

import android.view.View

fun View.clearScreenFocus() {
    this.apply {
        requestFocus()
        clearFocus()
    }
}