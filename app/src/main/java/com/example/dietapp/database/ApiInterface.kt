package com.example.dietapp.database

import com.example.dietapp.database.models.IngredientEntity
import com.example.dietapp.database.models.IngredientEntityList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("ingredients")
    fun getIngredientsAsync(): Deferred<Response<IngredientEntityList>>

    @GET("ingredients/{id}")
    fun getIngredientAsync(@Path("id") id: Int): Deferred<Response<IngredientEntity>>
}