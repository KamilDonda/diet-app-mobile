package com.example.dietapp.database.models.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientEntity(
    val carbohydrates: String,
    val fats: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kcal: String,
    val name: String,
    val proteins: String,
    val tags: String?
)