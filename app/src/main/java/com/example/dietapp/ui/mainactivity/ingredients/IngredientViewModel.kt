package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.services.DatabaseService
import com.example.dietapp.ui.filter.FilterViewModel
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class IngredientViewModel(private val dbService: DatabaseService) : FilterViewModel() {

    private val _ingredients = arrayListOf<IngredientEntity>()

    val ingredients = MutableLiveData(ArrayList<IngredientEntity>())

    var currentIngredient: IngredientEntity? = null
        private set

    fun setCurrentIngredient(currentIngredient: IngredientEntity) {
        this.currentIngredient = currentIngredient
    }

    fun setCurrentIngredient(position: Int) {
        this.currentIngredient = ingredients.value!![position]
    }

    fun prepareIngredients() {
        if (_ingredients.isEmpty()) {
            viewModelScope.launch {
                _ingredients.addAll(dbService.db.ingredientDao().selectAll())
                ingredients.postValue(_ingredients)
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

        var data = _ingredients.filter {
            it.name.contains(searchText, true) &&
                    it.kcal >= caloriesMin &&
                    it.proteins >= proteinsMin &&
                    it.fats >= fatsMin &&
                    it.carbohydrates >= carbsMin
        } as ArrayList

        if (caloriesMax != null && caloriesMax != 0f) {
            data = data.filter { it.kcal < caloriesMax } as ArrayList
        }
        if (proteinsMax != null && proteinsMax != 0f) {
            data = data.filter { it.proteins < proteinsMax } as ArrayList
        }
        if (fatsMax != null && fatsMax != 0f) {
            data = data.filter { it.fats < fatsMax } as ArrayList
        }
        if (carbsMax != null && carbsMax != 0f) {
            data = data.filter { it.carbohydrates < carbsMax } as ArrayList
        }

        when (f.order) {
            0 -> data.sortWith(Comparator.comparing(IngredientEntity::name, Collator.getInstance()))
            1 -> data.sortWith(
                Comparator.comparing(
                    IngredientEntity::name,
                    Collator.getInstance().reversed()
                )
            )
            2 -> data.sortBy { it.kcal }
            3 -> data.sortByDescending { it.kcal }
            4 -> data.sortBy { it.proteins }
            5 -> data.sortByDescending { it.proteins }
            6 -> data.sortBy { it.carbohydrates }
            7 -> data.sortByDescending { it.carbohydrates }
            8 -> data.sortBy { it.fats }
            9 -> data.sortByDescending { it.fats }
        }
        ingredients.postValue(data)
    }

    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) {
        state = parcelable
    }

    fun restoreRecyclerViewState(): Parcelable = state
    fun stateInitialized(): Boolean = ::state.isInitialized
}