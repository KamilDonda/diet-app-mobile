package com.example.dietapp.database.retrofit

import com.example.dietapp.database.models.ingredient.IngredientEntityList
import com.example.dietapp.database.models.meal.MealEntityList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("ingredients")
    fun getIngredientsAsync(): Deferred<Response<IngredientEntityList>>

//    @GET("ingredients/{id}")
//    fun getIngredientAsync(@Path("id") id: Int): Deferred<Response<IngredientEntity>>

    @GET("meals")
    fun getMealsAsync(): Deferred<Response<MealEntityList>>

//    @GET("meals/{id}")
//    fun getMealAsync(@Path("id") id: Int): Deferred<Response<MealEntity>>
}