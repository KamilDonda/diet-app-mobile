package com.example.dietapp.utils

import java.util.*

class DateUtil {
    companion object {
        const val DAY = 86400000L

        fun getCurrentDay(): Long {
            val date = Calendar.getInstance().time
            return Date(date.year, date.month, date.date).time
        }

        fun difference(a: Long, b: Long): Long {
            return ((a - b) / DAY)
        }

        fun longToDate(long: Long): Date {
            return Date(long)
        }
    }
}