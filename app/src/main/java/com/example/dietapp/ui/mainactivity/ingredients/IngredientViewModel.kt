package com.example.dietapp.ui.mainactivity.ingredients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.database.models.ingredient.IngredientEntity

class IngredientViewModel : ViewModel() {

    val ingredients = MutableLiveData(ArrayList<IngredientEntity>()).apply {
        value = prepareIngredients()
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

        ingredients.add(IngredientEntity("20", "20", 1, "20", "Ser", "20", ""))
        ingredients.add(IngredientEntity("20", "20", 1, "20", "Ser", "20", ""))
        ingredients.add(IngredientEntity("20", "20", 1, "20", "Ser", "20", ""))

        return ingredients
    }
}