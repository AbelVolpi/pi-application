package com.projetointegrador.pi_application.core

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {

        private lateinit var instance: Application

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

}