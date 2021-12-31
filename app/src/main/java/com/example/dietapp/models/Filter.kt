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
) {
    private fun getRange(min: Int, max: Int?, name: String, suffix: String): String? {
        return when {
            min == 0 && (max != 0 && max != null) -> "$name: do $max $suffix"
            min != 0 && (max != 0 && max != null) -> "$name: od $min do $max $suffix"
            min != 0 && (max == 0 || max == null) -> "$name: od $min $suffix"
            else -> null
        }
    }

    fun getRanges() = listOf(
        getRange(caloriesMin, caloriesMax, "Kalorie", "kcal"),
        getRange(proteinsMin, proteinsMax, "Białko", "g"),
        getRange(fatsMin, fatsMax, "Tłuszcze", "g"),
        getRange(carbsMin, carbsMax, "Węglowodany", "g")
    )
}