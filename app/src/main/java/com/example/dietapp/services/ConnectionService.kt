package com.example.dietapp.services

import android.util.Log
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionService {
    private suspend fun downloadIngredients(): IngredientEntityList {
        return try {
            RetrofitBuilder
                .instance
                .getIngredientsAsync()
                .await()
                .body()!!
        } catch (e: Exception) {
            ArrayList<IngredientEntity>() as IngredientEntityList
        }
    }

    fun synchronize() {
        CoroutineScope(Dispatchers.IO).launch {
            val ingredientEntityList = downloadIngredients()

            Log.v("ttt", ingredientEntityList.toString())
        }
    }
}