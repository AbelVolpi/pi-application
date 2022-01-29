package com.projetointegrador.pi_application.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Patterns
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.projetointegrador.pi_application.databinding.DialogLayoutBinding

object Utils {

    fun validateEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true
        return false
    }

    fun validatePassword(password: String): Boolean {
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

    fun showDialogAbout(context: Context, layoutInflater: LayoutInflater) {
        val dialog = AlertDialog.Builder(context)

        val view = DialogLayoutBinding.inflate(
            layoutInflater, null, false
        )

        dialog.apply {
            setView(view.root)
            create()
            show()
        }
    }

}