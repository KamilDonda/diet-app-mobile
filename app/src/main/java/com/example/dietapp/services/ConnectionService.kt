package com.example.dietapp.services

import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import com.example.dietapp.database.models.mealingredient.MealIngredientEntityList
import com.example.dietapp.database.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionService(private val dbService: DatabaseService) {
    private suspend fun downloadIngredients(): IngredientEntityList? {
        return try {
            RetrofitBuilder
                .instance
                .getIngredientsAsync()
                .await()
                .body()!!
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun downloadMeals(): MealEntityList? {
        return try {
            RetrofitBuilder
                .instance
                .getMealsAsync()
                .await()
                .body()!!
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun downloadMealsIngredient(): MealIngredientEntityList? {
        return try {
            RetrofitBuilder
                .instance
                .getMealsIngredientAsync()
                .await()
                .body()!!
        } catch (e: Exception) {
            null
        }
    }

    fun synchronize() {
        CoroutineScope(Dispatchers.IO).launch {
            downloadIngredients()?.let { dbService.db.ingredientDao().insertAll(it) }
            downloadMeals()?.let { dbService.db.mealDao().insertAll(it) }
            downloadMealsIngredient()?.let { dbService.db.mealIngredientDao().insertAll(it) }
        }
    }
}