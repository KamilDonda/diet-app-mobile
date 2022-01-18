package com.example.dietapp.database.retrofit

import com.example.dietapp.database.models.diet.DietEntityList
import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import com.example.dietapp.database.models.mealingredient.MealIngredientEntityList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("ingredients")
    fun getIngredientsAsync(): Deferred<Response<IngredientEntityList>>

//    @GET("ingredients/{id}")
//    fun getIngredientAsync(@Path("id") id: Int): Deferred<Response<IngredientEntity>>

    @GET("meals")
    fun getMealsAsync(): Deferred<Response<MealEntityList>>

//    @GET("meals/{id}")
//    fun getMealAsync(@Path("id") id: Int): Deferred<Response<MealEntity>>

    @GET("meals_ingredients")
    fun getMealsIngredientAsync(): Deferred<Response<MealIngredientEntityList>>

//    @GET("meals_ingredients/{id}")
//    fun getMealIngredientAsync(@Path("id") id: Int): Deferred<Response<MealIngredientEntity>>

    @GET("generate_diet")
    fun getGeneratedDietAsync(@Query("uid") uid: String): Deferred<Response<DietEntityList>>
}