package com.example.dietapp.database.repositories

import com.example.dietapp.database.models.meal.MealEntity
import com.example.dietapp.models.Diet
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

    suspend fun getDietOfWeek(): ArrayList<Diet> {
        val dietList = db.dietDao().selectAll()
        val ingredients = db.ingredientDao().selectAll()
        val meals = db.mealDao().selectAll()
        val mealIngredient = db.mealIngredientDao().selectAll()

        val dietOfWeek = ArrayList<Diet>()
        dietList.forEach { diet ->
            val mealEntities = ArrayList<MealEntity>()
            diet.getIds().forEach { mealId ->
                meals.find { mealEntity -> mealEntity.id == mealId }?.let { mealEntities.add(it) }
            }

            val mealsOfDay = getMeals(mealEntities, ingredients, mealIngredient)
            dietOfWeek.add(Diet(mealsOfDay[0], mealsOfDay[1], mealsOfDay[2], diet.date!!))
        }
        return dietOfWeek
    }
}