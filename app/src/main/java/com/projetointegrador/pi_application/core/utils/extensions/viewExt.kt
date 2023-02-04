package com.projetointegrador.pi_application.core.utils.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.projetointegrador.pi_application.R

fun View.clearScreenFocus() {
    this.apply {
        requestFocus()
        clearFocus()
    }
}

fun ImageView.setImageUsingGlide(context: Context, url: String) {
    Glide.with(context).load(url)
        .error(ContextCompat.getDrawable(context, R.drawable.blue_heart))
        .into(this)
}
