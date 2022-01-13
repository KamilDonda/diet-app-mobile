package com.example.dietapp.sharedpreferences

import android.content.Context
import com.example.dietapp.database.models.User
import com.google.gson.Gson


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

    fun getUserId(): String {
        return preferences.getString(USER_ID, "") ?: ""
    }

    fun setUserId(value: String) {
        preferences.edit().putString(USER_ID, value).apply()
    }

    fun setProfileData(user: User) {
        preferences.edit().putString(PROFILE_DATA, Gson().toJson(user)).apply()
    }

    fun getProfileData(): User {
        return try {
            Gson().fromJson(preferences.getString(PROFILE_DATA, ""), User::class.java)
        } catch (e: Exception) {
            User(getUserId())
        }
    }

    companion object {
        const val IS_LOGGED = "IS_LOGGED"
        const val USER_ID = "USER_ID"
        const val PROFILE_DATA = "PROFILE_DATA"
    }
}