package com.example.dietapp.database.models.meal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealEntity(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)