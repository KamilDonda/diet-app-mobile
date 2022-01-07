package com.example.dietapp.database.models.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientEntity(
    val carbohydrates: Float,
    val fats: Float,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kcal: Float,
    val name: String,
    val proteins: Float,
    val tags: String?
)