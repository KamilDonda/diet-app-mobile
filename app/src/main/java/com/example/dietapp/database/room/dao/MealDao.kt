package com.example.dietapp.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dietapp.database.models.meal.MealEntity

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MealEntity>)

    @Query("SELECT * FROM MealEntity")
    suspend fun selectAll(): List<MealEntity>
}