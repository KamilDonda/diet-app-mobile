package com.example.dietapp.utils

import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.database.models.meal.MealEntity
import com.example.dietapp.database.models.mealingredient.MealIngredientEntity
import com.example.dietapp.models.Meal

class EntitiesToMealConverter {
    companion object {
        private fun createMeal(
            mealEntity: MealEntity,
            ingredientEntities: List<IngredientEntity>,
            mealIngredient: List<MealIngredientEntity>
        ): Meal {
            var carbohydrates = 0.0
            var fats = 0.0
            var kcal = 0.0
            var proteins = 0.0
            val ingredients = ArrayList<String>()

            ingredientEntities.zip(mealIngredient).forEach {
                val ing = it.first
                val amount = it.second.amount

                carbohydrates += ing.carbohydrates * amount
                fats += ing.fats * amount
                kcal += ing.kcal * amount
                proteins += ing.proteins * amount
                ingredients.add(it.second.description)
            }
            return Meal(
                mealEntity.id,
                mealEntity.name,
                mealEntity.description,
                mealEntity.category,
                kcal.toFloat(),
                proteins.toFloat(),
                carbohydrates.toFloat(),
                fats.toFloat(),
                ingredients
            )
        }

        fun getMeals(
            mealEntities: List<MealEntity>,
            ingredientEntities: List<IngredientEntity>,
            mealIngredient: List<MealIngredientEntity>
        ): ArrayList<Meal> {
            val meals = ArrayList<Meal>()
            mealEntities.forEach { mealEntity ->
                val id = mealEntity.id
                val ingredients = ArrayList<IngredientEntity>()
                val relations = mealIngredient.filter { it.meal_id == id }

                relations.forEach {
                    ingredients.add(ingredientEntities.find { ingredient ->
                        it.ingredient_id == ingredient.id
                    }!!)
                }
                meals.add(createMeal(mealEntity, ingredients, relations))
            }
            return meals
        }
    }
}