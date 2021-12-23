package com.example.dietapp.services

import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.database.sharedpreferences.Preferences

class LogoutService(private val sharedPreferences: Preferences) {

    fun logout(activity: AppCompatActivity) {
        sharedPreferences.setIsLogged(false)
        activity.finish()
    }
}