package com.example.dietapp.database.models.diet

import java.io.Serializable

data class DietEntity(
    val id: Int,
    val breakfast: Int = -1,
    val dinner: Int = -1,
    val supper: Int = -1,
    val date: Long? = null
) : Serializable {

    constructor() : this(0)

    fun getIds() = listOf(this.breakfast, this.dinner, this.supper)
}