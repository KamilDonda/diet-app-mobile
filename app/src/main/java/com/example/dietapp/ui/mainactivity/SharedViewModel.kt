package com.example.dietapp.ui.mainactivity

import androidx.lifecycle.ViewModel
import com.example.dietapp.database.models.diet.DietEntity

class SharedViewModel : ViewModel() {
    var isEditModeOn = false
        private set

    fun changeEditMode(mode: Boolean = !isEditModeOn) {
        clear()
        isEditModeOn = mode
    }

    var position: Int? = null
        private set

    fun setPosition(position: Int) {
        this.position = position
    }

    var newMeals = DietEntity()
        private set

    fun updateNewMeal(id: Int) {
        newMeals = when (position) {
            0 -> newMeals.copy(breakfast = id)
            1 -> newMeals.copy(dinner = id)
            2 -> newMeals.copy(supper = id)
            else -> newMeals
        }
    }

    private fun clear() {
        position = null
        newMeals = DietEntity()
    }
}