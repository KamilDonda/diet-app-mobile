package com.example.dietapp.models

data class Diet(
    val breakfast: Meal,
    val dinner: Meal,
    val supper: Meal,
    val date: Long
) {
    fun getProteins() = breakfast.proteins + dinner.proteins + supper.proteins
    fun getCarbs() = breakfast.carbs + dinner.carbs + supper.carbs
    fun getFats() = breakfast.fats + dinner.fats + supper.fats
    fun getKcal() = breakfast.kcal + dinner.kcal + supper.kcal
}
