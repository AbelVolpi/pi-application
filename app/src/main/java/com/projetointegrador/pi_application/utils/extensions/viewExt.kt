package com.projetointegrador.pi_application.utils.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.clearScreenFocus() {
    this.apply {
        requestFocus()
        clearFocus()
    }
}

fun ImageView.setImageUsingGlide(context: Context, url: String) {
    Glide.with(context).load(url)
        .into(this)
}
