package com.example.dietapp.ui.loginactivity.register

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.dietapp.services.LoginService
import com.example.dietapp.utils.PasswordUtil

class RegisterViewModel(private val loginService: LoginService) : ViewModel() {

    var email: String = ""
        private set

    fun setEmail(email: String) {
        this.email = email
    }

    var newPassword: String = ""
        private set

    fun setNewPassword(password: String): Int {
        this.newPassword = password
        return PasswordUtil.securityLevel(newPassword)
    }

    var repeatedPassword: String = ""
        private set

    fun setRepeatedPassword(password: String) {
        this.repeatedPassword = password
    }

    fun clearData() {
        setEmail("")
        setNewPassword("")
        setRepeatedPassword("")
    }

    fun login(fragment: Fragment, context: Context, uid: String) {
        loginService.login(fragment, context, uid)
        clearData()
    }
}