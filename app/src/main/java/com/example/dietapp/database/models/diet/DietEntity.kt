package com.example.dietapp.database.models.diet

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DietEntity(
    @PrimaryKey()
    val id: Int,
    val breakfast: Int = -1,
    val dinner: Int = -1,
    val supper: Int = -1,
) : Serializable {

    constructor() : this(0)

    fun getIds() = listOf(this.breakfast, this.dinner, this.supper)
}