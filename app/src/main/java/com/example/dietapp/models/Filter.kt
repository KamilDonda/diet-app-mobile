package com.example.dietapp.models

data class Filter(
    val order: Int = 0,
    val caloriesMin: Int = 0,
    val caloriesMax: Int? = null,
    val proteinsMin: Int = 0,
    val proteinsMax: Int? = null,
    val fatsMin: Int = 0,
    val fatsMax: Int? = null,
    val carbsMin: Int = 0,
    val carbsMax: Int? = null,
    val isChecked: Boolean = false
)