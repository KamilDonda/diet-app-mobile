package com.example.dietapp.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dietapp.database.models.mealingredient.MealIngredientEntity

@Dao
interface MealIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MealIngredientEntity>)

    @Query("SELECT * FROM MealIngredientEntity")
    suspend fun selectAll(): List<MealIngredientEntity>
}