package com.example

import android.app.Application
import com.example.shared.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences
        prefs.initialize(this)
        super.onCreate()
    }
}