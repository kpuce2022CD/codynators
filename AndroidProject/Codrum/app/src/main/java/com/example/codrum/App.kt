package com.example.codrum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){
    init{
        instance = this
    }

    companion object{
        lateinit var instance : App
    }
}