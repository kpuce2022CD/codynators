package com.example.shared

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    private const val APP_PREFERENCE = "app_prefs"
    private const val ACCESS_TOKEN_KEY = "kkilog_access_token"
    private const val FISRT_CHECK_KEY = "kkilog_onboarding"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
    }

    var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN_KEY, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN_KEY, value).apply()

    var appFirstCheck: Boolean
        get() = prefs.getBoolean(FISRT_CHECK_KEY, false)
        set(value) = prefs.edit().putBoolean(FISRT_CHECK_KEY, value).apply()

    fun clearAccessToken() {
        prefs.edit().apply {
            remove(ACCESS_TOKEN_KEY)
            apply()
        }
    }

    fun isAccessTokenEmpty(): Boolean = accessToken.isNullOrEmpty()
}