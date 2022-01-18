package com.example.dietapp.services

import androidx.lifecycle.LiveData
import com.example.dietapp.sharedpreferences.Preferences

class LoginService(
    private val sharedPreferences: Preferences,
    private val connectionService: ConnectionService
) {

    fun login(uid: String): LiveData<Boolean> {
        sharedPreferences.setIsLogged(true)
        sharedPreferences.setUserId(uid)
        return synchronize(uid)
    }

    fun synchronize(uid: String? = null): LiveData<Boolean> {
        return connectionService.synchronize(uid)
    }
}