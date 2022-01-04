package com.example.dietapp.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.database.room.dao.IngredientDao

@Database(
    entities = [
        IngredientEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class Db : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

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