package com.example.dietapp.database.repositories

import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.services.DatabaseService

class IngredientRepo(private val dbService: DatabaseService) {
    suspend fun getAll(): List<IngredientEntity> {
        return dbService.db.ingredientDao().selectAll()
    }
}