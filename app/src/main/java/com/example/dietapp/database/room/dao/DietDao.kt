package com.example.dietapp.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dietapp.database.models.diet.DietEntity

@Dao
interface DietDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DietEntity>)

    @Query("SELECT * FROM DietEntity")
    suspend fun selectAll(): List<DietEntity>
}