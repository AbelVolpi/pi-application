package com.projetointegrador.pi_application.utils.extensions

import android.content.res.Resources

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()