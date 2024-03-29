package com.example.dietapp.ui.mainactivity.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.database.models.User
import com.example.dietapp.services.FirebaseService
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.utils.PasswordUtil
import kotlin.math.round

class ProfileViewModel(
    private val firebaseService: FirebaseService,
    private val sharedPreferences: Preferences
) : ViewModel() {

    fun getUserId() = sharedPreferences.getUserId()

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

    var gender: Boolean? = null
        private set

    fun setGender(gender: Boolean?) {
        this.gender = gender
    }

    var goal: Int? = null
        private set

    fun setGoal(goal: Int?) {
        this.goal = goal
    }

    var activity: Int? = null
        private set

    fun setActivity(activity: Int?) {
        this.activity = activity
    }

    var age: Int? = null
        private set

    fun setAge(age: Int?) {
        this.age = age
    }

    var weight: Float? = null
        private set

    fun setWeight(weight: Float?) {
        this.weight = weight
    }

    var height: Float? = null
        private set

    fun setHeight(height: Float?) {
        this.height = height
    }

    var preferences = ArrayList<String>()
        private set

    fun setPreferences(pref: List<String>) {
        preferences.clear()
        preferences.addAll(pref)
    }

    val bmi = MutableLiveData("")

    fun setBMI() {
        if (weight != null && height != null) {
            val bmi = weight!! / ((height!! / 100) * (height!! / 100))
            this.bmi.value = (round(bmi * 100) / 100).toString()
        }
    }

    fun reset() {
        val user = sharedPreferences.getProfileData()
        setGender(user.gender)
        setGoal(user.goal)
        setActivity(user.activity)
        setAge(user.age)
        setWeight(user.weight)
        setHeight(user.height)
        setPreferences(user.preferences)
    }

    fun save(user: User) {
        firebaseService.updateUser(user)
        sharedPreferences.setProfileData(user)
    }
}