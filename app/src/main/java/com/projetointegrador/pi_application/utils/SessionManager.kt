package com.projetointegrador.pi_application.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.projetointegrador.pi_application.MainApplication

object SessionManager {

    private const val SESSION_PREFERENCES_FILE = "shared_preferences_file"
    private const val USER_NAME = "user_name"

    private val prefs: SharedPreferences by lazy {
        MainApplication.applicationContext()
            .getSharedPreferences(SESSION_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }


    fun updateUserName(userName: String) {
        prefs.edit {
            putString(USER_NAME, userName)
        }
    }


}