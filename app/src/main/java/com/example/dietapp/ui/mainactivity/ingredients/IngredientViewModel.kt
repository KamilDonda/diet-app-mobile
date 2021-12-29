package com.example.dietapp.ui.mainactivity.ingredients

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.ui.filter.FilterViewModel

class IngredientViewModel : FilterViewModel() {

    private val _ingredients = prepareIngredients()

    val ingredients = MutableLiveData(ArrayList<IngredientEntity>()).apply {
        value = _ingredients
    }

    var currentIngredient: IngredientEntity? = null
        private set

    fun setCurrentIngredient(currentIngredient: IngredientEntity) {
        this.currentIngredient = currentIngredient
    }

    fun setCurrentIngredient(position: Int) {
        this.currentIngredient = ingredients.value!![position]
    }

    private fun prepareIngredients(): ArrayList<IngredientEntity> {
        val ingredients = ArrayList<IngredientEntity>()

        ingredients.add(IngredientEntity("20", "30", 1, "240", "Ser", "20", ""))
        ingredients.add(IngredientEntity("10", "20", 2, "220", "Masło", "50", ""))
        ingredients.add(IngredientEntity("30", "10", 3, "250", "Chleb", "10", ""))
        ingredients.add(IngredientEntity("35", "10", 4, "250", "Szynka", "10", ""))
        ingredients.add(IngredientEntity("12", "15", 5, "250", "Pieczarka", "10", ""))
        ingredients.add(IngredientEntity("53", "62", 6, "250", "Pomidor", "10", ""))

        return ingredients
    }

    var searchText: String = ""
        private set

    fun setSearchText(searchText: String) {
        this.searchText = searchText
    }

    fun search() {
        ingredients.postValue(_ingredients.filter {
            it.name.contains(
                searchText,
                true
            )
        } as ArrayList)

        Log.v("ttt", "filters: $filter")
    }
}