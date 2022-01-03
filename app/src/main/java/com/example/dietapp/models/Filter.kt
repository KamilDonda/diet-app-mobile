package com.example.dietapp.models

import android.content.Context
import com.example.dietapp.R
import com.example.dietapp.utils.getAsArrayList

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
) {
    private fun getRange(min: Int, max: Int?, name: String, suffix: String): String? {
        return when {
            min == 0 && (max != 0 && max != null) -> "$name: do $max $suffix"
            min != 0 && (max != 0 && max != null) -> "$name: od $min do $max $suffix"
            min != 0 && (max == 0 || max == null) -> "$name: od $min $suffix"
            else -> null
        }
    }

    fun getTextsForChips(context: Context) = listOf(
        context.resources.getStringArray(R.array.filter_order).getAsArrayList()[order],
        getRange(caloriesMin, caloriesMax, context.getString(R.string.calories), "kcal"),
        getRange(proteinsMin, proteinsMax, context.getString(R.string.proteins), "g"),
        getRange(fatsMin, fatsMax, context.getString(R.string.fats), "g"),
        getRange(carbsMin, carbsMax, context.getString(R.string.carbs), "g")
    )
}