package com.projetointegrador.pi_application.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.inputmethod.InputMethodManager

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

}