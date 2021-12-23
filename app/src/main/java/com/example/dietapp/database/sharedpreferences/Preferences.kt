package com.example.dietapp.database.sharedpreferences

import android.content.Context

class Preferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "Shared_Preferences",
        Context.MODE_PRIVATE
    )

    fun getIsLogged(): Boolean {
        return preferences.getBoolean(IS_LOGGED, false)
    }

    fun setIsLogged(value: Boolean) {
        preferences.edit().putBoolean(IS_LOGGED, value).apply()
    }

    companion object {
        const val IS_LOGGED = "IS_LOGGED"
    }
}