package com.framework.openweatherandroidapp.utils

import android.content.SharedPreferences
import javax.inject.Inject


class SharedPrefsUtility @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun putData(key: String, data: Int) {
        sharedPreferences.edit().putInt(key, data).apply()
    }

    fun getData(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_user_logged_in",false)
    }
}