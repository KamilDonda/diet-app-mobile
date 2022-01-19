package com.example.dietapp.ui.mainactivity

import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var isEditModeOn = false
        private set

    fun changeEditMode(mode: Boolean = !isEditModeOn) {
        isEditModeOn = mode
    }
}