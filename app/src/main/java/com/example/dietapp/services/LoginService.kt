package com.example.dietapp.services

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.ui.mainactivity.MainActivity

class LoginService(
    private val sharedPreferences: Preferences,
    private val connectionService: ConnectionService
) {

    fun login(fragment: Fragment, context: Context, uid: String) {
        sharedPreferences.setIsLogged(true)
        sharedPreferences.setUserId(uid)
        navigateToHome(fragment, context)
    }

    fun navigateToHome(fragment: Fragment, context: Context) {
        connectionService.synchronize()
        val intent = Intent(context, MainActivity::class.java)
        fragment.startActivity(intent)
    }
}