package com.example.dietapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.utils.PasswordUtil

class ProfileViewModel : ViewModel() {

    val hasInputFocus = MutableLiveData<Boolean?>(null)

    var oldPassword: String = ""
        private set

    fun setOldPassword(password: String) {
        oldPassword = password
    }

    var newPassword: String = ""
        private set

    fun setNewPassword(password: String): Int {
        newPassword = password
        return PasswordUtil.securityLevel(newPassword)
    }

    var repeatedPassword: String = ""
        private set

    fun setRepeatedPassword(password: String) {
        repeatedPassword = password
    }
}