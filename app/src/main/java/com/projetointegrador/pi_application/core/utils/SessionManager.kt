package com.projetointegrador.pi_application.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context
    ) {
        private val prefs: SharedPreferences by lazy {
            context.getSharedPreferences(SESSION_PREFERENCES_FILE, Context.MODE_PRIVATE)
        }

        fun saveUserData(
            userId: String,
            userEmail: String
        ) {
            prefs.edit {
                putString(USER_ID, userId)
                putString(USER_EMAIL, userEmail)
            }
        }

        fun updateUserName(userName: String) {
            prefs.edit { putString(USER_NAME, userName) }
        }

        fun getUserEmail(): String? = prefs.getString(USER_EMAIL, "")

        fun getUserId(): String? = prefs.getString(USER_ID, "")

        fun setUserId(userId: String) {
            prefs.edit { putString(USER_ID, userId) }
        }

        fun logout() {
            prefs.edit().clear().apply()
        }

        companion object {
            private const val SESSION_PREFERENCES_FILE = "shared_preferences_file"
            private const val USER_NAME = "user_name"
            private const val USER_EMAIL = "user_email"
            private const val USER_ID = "user_id"
        }
    }
