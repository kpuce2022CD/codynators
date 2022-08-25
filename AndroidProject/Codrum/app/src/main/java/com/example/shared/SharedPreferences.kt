package com.example.shared

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    private const val APP_PREFERENCE = "app_prefs"
    private const val LOGIN_CHECK_KEY = "codynators_login"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
    }

    var loginCheck: Boolean
        get() = prefs.getBoolean(LOGIN_CHECK_KEY, false)
        set(value) = prefs.edit().putBoolean(LOGIN_CHECK_KEY, value).apply()
}