package com.projetointegrador.pi_application.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object Utils {

    fun Activity.hideSoftKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    fun validateEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true
        return false
    }
    fun validatePassword(password: String): Boolean{
        if (password.isNotEmpty()) return true
        return false
    }


    fun bitmapFromResource(resource: Int, context: Context): BitmapDescriptor? {

        val drawable = ContextCompat.getDrawable(context, resource)

        drawable?.run {
            val canvas = Canvas()
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        return null
    }

}