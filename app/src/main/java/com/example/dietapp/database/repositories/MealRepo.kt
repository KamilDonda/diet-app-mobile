package com.example.dietapp.database.repositories

import com.example.dietapp.models.Meal
import com.example.dietapp.services.DatabaseService
import com.example.dietapp.utils.EntitiesToMealConverter.Companion.getMeals

class MealRepo(dbService: DatabaseService) {
    private val db = dbService.db

    suspend fun getAll(): List<Meal> {
        val ingredients = db.ingredientDao().selectAll()
        val meals = db.mealDao().selectAll()
        val mealIngredient = db.mealIngredientDao().selectAll()

        return getMeals(meals, ingredients, mealIngredient)
    }
}