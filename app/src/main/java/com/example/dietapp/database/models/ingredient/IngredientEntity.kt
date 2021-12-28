package com.example.dietapp.database.models.ingredient

data class IngredientEntity(
    val carbohydrates: String,
    val fats: String,
    val id: Int,
    val kcal: String,
    val name: String,
    val proteins: String,
    val tags: Any
)