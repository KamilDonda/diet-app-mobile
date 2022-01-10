package com.example.dietapp.ui.mainactivity.meals

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dietapp.database.repositories.MealRepo
import com.example.dietapp.models.Meal
import com.example.dietapp.ui.filter.FilterViewModel
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class MealViewModel(
    private val mealRepo: MealRepo
) : FilterViewModel() {

    private val _meals = arrayListOf<Meal>()

    val meals = MutableLiveData(ArrayList<Meal>())

    var currentMeal: Meal? = null
        private set

    fun setCurrentMeal(meal: Meal) {
        currentMeal = meal
    }

    fun setCurrentMeal(position: Int) {
        currentMeal = meals.value!![position]
    }

    fun prepareMeals() {
        if (_meals.isEmpty()) {
            viewModelScope.launch {
                _meals.addAll(mealRepo.getAll())
                meals.postValue(_meals)
            }
        }
    }

    var searchText: String = ""
        private set

    fun setSearchText(searchText: String) {
        this.searchText = searchText
    }

    fun search() {
        val f = this.filter

        var caloriesMin = f.caloriesMin.toFloat()
        var caloriesMax = f.caloriesMax?.toFloat()
        var proteinsMin = f.proteinsMin.toFloat()
        var proteinsMax = f.proteinsMax?.toFloat()
        var fatsMin = f.fatsMin.toFloat()
        var fatsMax = f.fatsMax?.toFloat()
        var carbsMin = f.carbsMin.toFloat()
        var carbsMax = f.carbsMax?.toFloat()

        if (f.isChecked) {
            caloriesMin = caloriesMin.div(100)
            caloriesMax = caloriesMax?.div(100)
            proteinsMin = proteinsMin.div(100)
            proteinsMax = proteinsMax?.div(100)
            fatsMin = fatsMin.div(100)
            fatsMax = fatsMax?.div(100)
            carbsMin = carbsMin.div(100)
            carbsMax = carbsMax?.div(100)
        }

        var data = _meals.filter {
            it.name.contains(searchText, true) &&
                    it.kcal.toFloat() >= caloriesMin &&
                    it.proteins.toFloat() >= proteinsMin &&
                    it.fats.toFloat() >= fatsMin &&
                    it.carbs.toFloat() >= carbsMin
        } as ArrayList

        if (caloriesMax != null && caloriesMax != 0f) {
            data = data.filter { it.kcal.toFloat() < caloriesMax } as ArrayList
        }
        if (proteinsMax != null && proteinsMax != 0f) {
            data = data.filter { it.proteins.toFloat() < proteinsMax } as ArrayList
        }
        if (fatsMax != null && fatsMax != 0f) {
            data = data.filter { it.fats.toFloat() < fatsMax } as ArrayList
        }
        if (carbsMax != null && carbsMax != 0f) {
            data = data.filter { it.carbs.toFloat() < carbsMax } as ArrayList
        }

        when (filter.order) {
            0 -> data.sortWith(Comparator.comparing(Meal::name, Collator.getInstance()))
            1 -> data.sortWith(Comparator.comparing(Meal::name, Collator.getInstance().reversed()))
            2 -> data.sortBy { it.kcal }
            3 -> data.sortByDescending { it.kcal }
            4 -> data.sortBy { it.proteins }
            5 -> data.sortByDescending { it.proteins }
            6 -> data.sortBy { it.carbs }
            7 -> data.sortByDescending { it.carbs }
            8 -> data.sortBy { it.fats }
            9 -> data.sortByDescending { it.fats }
        }

        meals.postValue(data)
    }

    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) {
        state = parcelable
    }

    fun restoreRecyclerViewState(): Parcelable = state
    fun stateInitialized(): Boolean = ::state.isInitialized
}