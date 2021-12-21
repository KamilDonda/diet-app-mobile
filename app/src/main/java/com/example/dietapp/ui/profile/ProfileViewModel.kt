package com.example.dietapp.ui.profile

import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var oldPassword: String = ""
        private set

    fun setOldPassword(password: String) {
        oldPassword = password
    }

    var newPassword: String = ""
        private set

    fun setNewPassword(password: String) {
        newPassword = password
    }

    var repeatedPassword: String = ""
        private set

    fun setRepeatedPassword(password: String) {
        repeatedPassword = password
    }
}