package com.example.dietapp.database.models

data class IngredientEntity(
    val carbohydrates: Double,
    val fats: Double,
    val id: Int,
    val kcal: Double,
    val name: String,
    val proteins: Double,
    val tags: Any
)