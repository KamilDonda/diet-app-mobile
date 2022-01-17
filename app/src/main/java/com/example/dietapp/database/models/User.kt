package com.example.dietapp.database.models

import com.example.dietapp.database.models.diet.DietEntity
import java.io.Serializable

data class User(
    val uid: String,
    var nickname: String = "",
    var gender: Boolean? = null,
    var age: Int? = null,
    var height: Float? = null,
    var weight: Float? = null,
    var activity: Int? = null,
    var goal: Int? = null,
    val preferences: ArrayList<String> = ArrayList(),
    val diet: ArrayList<DietEntity> = ArrayList()
) : Serializable {

    constructor() : this("", "")
}