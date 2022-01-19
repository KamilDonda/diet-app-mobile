package com.example.dietapp.utils

import android.graphics.Color

object PasswordUtil {
    fun securityLevel(password: String): Int {
        return passwordLength(password) + hasLowerAndUpperCase(password) + hasDigit(password) + hasSpecialChar(
            password
        )
    }

    fun getIndex(strength: Int): Int {
        return when {
            strength < 24 -> 0
            strength < 44 -> 1
            strength < 64 -> 2
            strength < 89 -> 3
            else -> 4
        }
    }

    fun getColor(index: Int): Int {
        return listOf(
            Color.parseColor("#ff0000"),
            Color.parseColor("#D32F2F"),
            Color.parseColor("#FFA000"),
            Color.parseColor("#689F38"),
            Color.parseColor("#64DD17")
        )[index]
    }

    private fun passwordLength(password: String): Int {
        val len = password.length
        return when {
            len < 8 -> {
                (len * 1.75).toInt()
            }
            len < 15 -> {
                (len * 2)
            }
            len < 20 -> {
                (len * 2.5).toInt()
            }
            else -> {
                50
            }
        }
    }

    private fun hasLowerAndUpperCase(password: String): Int {
//        var hasLowerCase = false
//        var hasUpperCase = false
//        password.forEach {
//            if (it.isLowerCase())
//                hasLowerCase = true
//            if (it.isUpperCase())
//                hasUpperCase = true
//        }
//        return if (hasLowerCase && hasUpperCase) 15 else 0
        return if (password.any { it.isLowerCase() } && password.any { it.isUpperCase() }) 15 else 0
    }

    private fun hasDigit(password: String): Int {
//        password.forEach {
//            if (it.isDigit())
//                return 15
//        }
//        return 0
        return if (password.any { it.isDigit() }) 15 else 0
    }

    private fun hasSpecialChar(password: String): Int {
        return if (password.any { !(it.isLetterOrDigit() || it.isWhitespace()) }) 20 else 0
    }
}
