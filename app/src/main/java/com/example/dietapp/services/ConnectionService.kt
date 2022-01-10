package com.example.dietapp.services

import android.util.Log
import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import com.example.dietapp.database.models.mealingredient.MealIngredientEntityList
import com.example.dietapp.database.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ConnectionService(private val dbService: DatabaseService) {

    companion object {
        const val TAG = "CONNECTION_SERVICE"
    }

    private suspend fun downloadIngredients(): IngredientEntityList? {
        return try {
            RetrofitBuilder
                .instance
                .getIngredientsAsync()
                .await()
                .body()!!
        } catch (e: Exception) {
            Log.v(TAG, e.stackTraceToString())
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
            Log.v(TAG, e.stackTraceToString())
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
            Log.v(TAG, e.stackTraceToString())
            null
        }
    }

    fun synchronize() {
        CoroutineScope(Dispatchers.IO).launch {
            downloadIngredients()?.let {
                dbService.db.ingredientDao()
                    .insertAll(it.map { entity ->
                        entity.copy(name = entity.name.capitalize(Locale.ROOT))
                    })
            }
            downloadMeals()?.let {
                dbService.db.mealDao().insertAll(it)
            }
            downloadMealsIngredient()?.let {
                dbService.db.mealIngredientDao().insertAll(it)
            }
        }
    }
}