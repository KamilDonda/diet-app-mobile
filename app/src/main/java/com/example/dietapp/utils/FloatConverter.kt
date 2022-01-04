package com.example.dietapp.utils

import kotlin.math.round

class FloatConverter {
    companion object {
        fun floatToString(float: Float, suffix: String = "", isChecked: Boolean = false): String {
            val f = if (isChecked) float * 100 else float
            val number = if (isWhole(f)) f.toInt() else f.round(1)
            return "$number $suffix".trim()
        }

        private fun isWhole(value: Float): Boolean {
            return value - value.toInt() == 0.0f
        }

        private fun Float.round(decimals: Int): Float {
            var multiplier = 1.0f
            repeat(decimals) { multiplier *= 10 }
            return round(this * multiplier) / multiplier
        }
    }
}