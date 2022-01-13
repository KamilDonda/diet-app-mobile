package com.example.dietapp.utils

class AgeConverter {
    companion object {
        fun intToString(age: Int?): String {
            if (age == null) {
                return "Wybierz"
            }
            val lastDigit = (age % 10)
            if (age > 20 && lastDigit in 2..4) {
                return "$age lata"
            }
            return "$age lat"
        }
    }
}