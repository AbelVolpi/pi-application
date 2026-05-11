package com.projetointegrador.pi_application.core.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import com.projetointegrador.pi_application.databinding.AboutDialogLayoutBinding

object Utils {
    fun validateEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true
        return false
    }

    fun validatePassword(password: String): Boolean {
        if (password.isNotEmpty()) return true
        return false
    }

    fun showAboutDialog(
        context: Context,
        layoutInflater: LayoutInflater,
    ) {
        val dialog = AlertDialog.Builder(context)

        val view =
            AboutDialogLayoutBinding.inflate(
                layoutInflater,
                null,
                false,
            )

        dialog.apply {
            setView(view.root)
            create()
            show()
        }
    }
}
