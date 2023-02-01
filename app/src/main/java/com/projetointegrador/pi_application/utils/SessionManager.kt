package com.projetointegrador.pi_application.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.projetointegrador.pi_application.core.MainApplication

object SessionManager {

    private const val SESSION_PREFERENCES_FILE = "shared_preferences_file"
    private const val USER_NAME = "user_name"
    private const val USER_EMAIL = "user_email"
    private const val USER_ID = "user_id"

    private val prefs: SharedPreferences by lazy {
        MainApplication.applicationContext()
            .getSharedPreferences(SESSION_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    fun saveUserData(userId: String, userEmail: String) {
        prefs.edit {
            putString(USER_ID, userId)
            putString(USER_EMAIL, userEmail)
        }
    }

    fun updateUserName(userName: String) {
        prefs.edit {
            putString(USER_NAME, userName)
        }
    }

    fun getUserEmail(): String? {
        return prefs.getString(USER_EMAIL, "")
    }

    fun getGetUserId(): String? {
        return prefs.getString(USER_ID, "")
    }

    fun setUserId(userId: String) {
        prefs.edit {
            putString(USER_ID, userId)
        }
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
