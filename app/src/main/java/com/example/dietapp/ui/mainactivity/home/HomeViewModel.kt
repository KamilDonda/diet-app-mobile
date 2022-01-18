package com.example.dietapp.ui.mainactivity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.R
import com.example.dietapp.database.repositories.MealRepo
import com.example.dietapp.models.Diet
import com.example.dietapp.models.MealHome
import com.example.dietapp.services.ConnectionService
import kotlinx.coroutines.launch

class HomeViewModel(
    private val connectionService: ConnectionService,
    private val mealRepo: MealRepo
) : ViewModel() {

    val dietOfWeek = MutableLiveData(ArrayList<Diet>())

    fun setDietOfWeek() {
        viewModelScope.launch {
            dietOfWeek.postValue(mealRepo.getDietOfWeek())
        }
    }

    var currentDiet: Diet? = null
        private set

    fun setCurrentDiet(diet: Diet) {
        currentDiet = diet
    }

    fun setCurrentDiet(position: Int) {
        currentDiet = dietOfWeek.value?.get(position)
    }

    val homeMeals = MutableLiveData(ArrayList<MealHome>())

    fun setHomeMeals() {
        val list = arrayListOf(
            MealHome(
                currentDiet!!.breakfast.name,
                currentDiet!!.breakfast.kcal,
                "Śniadanie",
                R.drawable.breakfast
            ),
            MealHome(
                currentDiet!!.dinner.name,
                currentDiet!!.dinner.kcal, "Obiad", R.drawable.lunch
            ),
            MealHome(
                currentDiet!!.supper.name,
                currentDiet!!.supper.kcal, "Kolacja", R.drawable.dinner
            )
        )
        homeMeals.postValue(list)
    }

    fun generateDiet(): LiveData<Boolean> {
        return connectionService.synchronizeDietWithApi()
    }
}