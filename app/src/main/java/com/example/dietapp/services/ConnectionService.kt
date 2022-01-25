package com.example.dietapp.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.database.models.diet.DietEntity
import com.example.dietapp.database.models.diet.DietEntityList
import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import com.example.dietapp.database.models.mealingredient.MealIngredientEntityList
import com.example.dietapp.database.retrofit.RetrofitBuilder
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.utils.DateUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ConnectionService(
    private val dbService: DatabaseService,
    private val firebaseService: FirebaseService,
    private val sharedPreferences: Preferences
) {

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

    private suspend fun downloadDiet(uid: String): DietEntityList? {
        return try {
            RetrofitBuilder
                .instance
                .getGeneratedDietAsync(uid)
                .await()
                .body()!!
        } catch (e: Exception) {
            Log.v(TAG, e.stackTraceToString())
            null
        }
    }

    fun synchronize(uid: String?): LiveData<Boolean> {
        val isFinished = MutableLiveData(false)

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

            if (uid != null) {
                try {
                    val user = firebaseService.getUserData(uid)
                    sharedPreferences.setProfileData(user)
                    isFinished.postValue(true)
                } catch (e: Exception) {
                    Log.v(TAG, e.stackTraceToString())
                }
            }
        }
        return isFinished
    }

    fun synchronizeDietWithApi(): LiveData<Boolean> {
        val isFinished = MutableLiveData(false)
        CoroutineScope(Dispatchers.IO).launch {
            val uid = sharedPreferences.getUserId()
            downloadDiet(uid)?.let {
                var id = 1

                val timeInMillis = DateUtil.getCurrentDay()
                val nextDay = DateUtil.DAY
                val dietList = it.mapIndexed { index, entity ->
                    entity.copy(id = id++, date = timeInMillis + index * nextDay)
                }
                sharedPreferences.setProfileData(
                    sharedPreferences.getProfileData()
                        .copy(diet = dietList as ArrayList<DietEntity>)
                )
                firebaseService.updateUserDiet(uid, dietList)

                isFinished.postValue(true)
            }
        }
        return isFinished
    }
}