package com.example.dietapp.ui.loginactivity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.services.LoginService
import com.example.dietapp.utils.PasswordUtil

class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    var email: String = ""
        private set

    fun setEmail(email: String) {
        this.email = email
    }

    var password: String = ""
        private set

    fun setPassword(password: String): Int {
        this.password = password
        return PasswordUtil.securityLevel(password)
    }

    fun clearData() {
        setEmail("")
        setPassword("")
    }

    fun login(uid: String): LiveData<Boolean> {
        clearData()
        return loginService.login(uid)
    }
}