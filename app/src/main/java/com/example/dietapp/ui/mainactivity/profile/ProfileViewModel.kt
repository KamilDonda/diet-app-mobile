package com.example.dietapp.ui.mainactivity.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.utils.PasswordUtil
import kotlin.math.round

class ProfileViewModel : ViewModel() {

    val hasInputFocus = MutableLiveData<Boolean?>(null)

    var oldPassword: String = ""
        private set

    fun setOldPassword(password: String) {
        this.oldPassword = password
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

    var gender: String = "Mężczyzna"
        private set

    fun setGender(gender: String) {
        this.gender = gender
    }

    var goal: String = "Utrzymanie wagi"
        private set

    fun setGoal(goal: String) {
        this.goal = goal
    }

    var activity: String = "Umiarkowana"
        private set

    fun setActivity(activity: String) {
        this.activity = activity
    }

    var age: Int = 20
        private set

    fun setAge(age: Int) {
        this.age = age
    }

    var weight: Float = 50f
        private set

    fun setWeight(weight: Float) {
        this.weight = weight
    }

    var height: Float = 170f
        private set

    fun setHeight(height: Float) {
        this.height = height
    }

    val bmi = MutableLiveData(0f)

    fun setBMI() {
        val bmi = weight / ((height / 100) * (height / 100))
        this.bmi.value = round(bmi * 100) / 100
    }
}