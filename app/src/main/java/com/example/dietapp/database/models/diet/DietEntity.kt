package com.example.dietapp.database.models.diet

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DietEntity(
    @PrimaryKey()
    val id: Int,
    val breakfast: Int,
    val dinner: Int,
    val supper: Int,
)