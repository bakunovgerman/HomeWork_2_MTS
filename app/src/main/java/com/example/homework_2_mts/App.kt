package com.example.homework_2_mts

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
    }

    companion object {
        lateinit var applicationContext: Context
            private set
    }
}