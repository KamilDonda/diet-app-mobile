package com.example.dietapp.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dietapp.database.models.ingredient.IngredientEntity

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<IngredientEntity>)

    @Query("SELECT * FROM IngredientEntity")
    suspend fun selectAll(): List<IngredientEntity>
}