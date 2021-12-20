package com.example.dietapp.models

data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val kcal: Float,
    val proteins: Float,
    val carbs: Float,
    val fats: Float,
    val ingredients: ArrayList<String> = ArrayList()
)