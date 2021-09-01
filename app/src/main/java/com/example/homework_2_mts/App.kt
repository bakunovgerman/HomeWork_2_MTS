package com.example.homework_2_mts

import android.app.Application
import android.content.Context
import com.example.homework_2_mts.repository.retrofit.ApiService

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
    }

    init {
        instance = this
    }

    val apiService: ApiService by lazy {
        ApiService.create()
    }

    companion object {
        lateinit var instance: App
            private set

        lateinit var applicationContext: Context
            private set
    }
}