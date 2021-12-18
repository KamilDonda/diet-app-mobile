package com.example.dietapp.utils

import kotlin.math.round

class FloatConverter {
    companion object {
        fun floatToString(float: Float, suffix: String = ""): String {
            val number = if (isWhole(float)) float.toInt() else float.round(1)
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