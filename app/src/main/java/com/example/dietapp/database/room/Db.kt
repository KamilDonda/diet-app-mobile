package com.example.dietapp.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.database.models.meal.MealEntity
import com.example.dietapp.database.models.mealingredient.MealIngredientEntity
import com.example.dietapp.database.room.dao.IngredientDao
import com.example.dietapp.database.room.dao.MealDao
import com.example.dietapp.database.room.dao.MealIngredientDao

@Database(
    entities = [
        IngredientEntity::class,
        MealEntity::class,
        MealIngredientEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class Db : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun mealDao(): MealDao
    abstract fun mealIngredientDao(): MealIngredientDao

    companion object {
        var db: Db? = null

        fun create(context: Context): Db {
            if (db == null) {
                db = Room.databaseBuilder(context.applicationContext, Db::class.java, "dietapp_db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return db as Db
        }
    }
}