package com.example.dietapp.ui.mainactivity.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.models.MealHome

class HomeViewModel : ViewModel() {

    val meals = MutableLiveData(ArrayList<MealHome>()).apply {
        value = arrayListOf(
            MealHome("Name1", 3200f, "Śniadanie"),
            MealHome("Name2", 3200f, "Obiad"),
            MealHome("Name3", 3200f, "Kolacja")
        )
    }
}