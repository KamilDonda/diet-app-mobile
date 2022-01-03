package com.example.dietapp.ui.mainactivity.ingredients

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
//        val data = _ingredients.filter { it.name.contains(searchText, true) } as ArrayList

        var data = _ingredients.filter {
            it.name.contains(searchText, true) &&
                    it.kcal.toFloat() > filter.caloriesMin &&
                    it.proteins.toFloat() > filter.proteinsMin &&
                    it.fats.toFloat() > filter.fatsMin &&
                    it.carbohydrates.toFloat() > filter.carbsMin
        } as ArrayList

        if (filter.caloriesMax != null && filter.caloriesMax != 0) {
            data = data.filter { it.kcal.toFloat() < filter.caloriesMax!! } as ArrayList
        }
        if (filter.proteinsMax != null && filter.proteinsMax != 0) {
            data = data.filter { it.proteins.toFloat() < filter.proteinsMax!! } as ArrayList
        }
        if (filter.fatsMax != null && filter.fatsMax != 0) {
            data = data.filter { it.fats.toFloat() < filter.fatsMax!! } as ArrayList
        }
        if (filter.carbsMax != null && filter.carbsMax != 0) {
            data = data.filter { it.carbohydrates.toFloat() < filter.carbsMax!! } as ArrayList
        }

        when (filter.order) {
            0 -> data.sortBy { it.name }
            1 -> data.sortByDescending { it.name }
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
}