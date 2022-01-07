package com.example.dietapp.database.models.mealingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealIngredientEntity(
    val amount: Double,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ingredient_name: Int,
    val meal_id: Int
)