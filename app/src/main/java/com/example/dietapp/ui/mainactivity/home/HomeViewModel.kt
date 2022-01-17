package com.example.dietapp.ui.mainactivity.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.R
import com.example.dietapp.models.MealHome
import com.example.dietapp.services.ConnectionService

class HomeViewModel(private val connectionService: ConnectionService) : ViewModel() {

    val meals = MutableLiveData(ArrayList<MealHome>()).apply {
        value = arrayListOf(
            MealHome("Name1", 3200f, "Śniadanie", R.drawable.breakfast),
            MealHome("Name2", 3200f, "Obiad", R.drawable.lunch),
            MealHome("Name3", 3200f, "Kolacja", R.drawable.dinner)
        )
    }

    fun generateDiet() {
        connectionService.synchronizeDiet()
    }
}