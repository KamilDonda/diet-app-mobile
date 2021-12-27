package com.example.dietapp.services

import android.util.Log
import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import com.example.dietapp.database.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionService {
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

    fun synchronize() {
        CoroutineScope(Dispatchers.IO).launch {
            val ingredientEntityList = downloadIngredients()
            val mealEntityList = downloadMeals()

            Log.v("ttt", ingredientEntityList.toString())
            Log.v("ttt", mealEntityList.toString())
        }
    }
}