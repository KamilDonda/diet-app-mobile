package com.example.dietapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
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

        fun getStartOfWeek(): Date {
            val currentDat = getCurrentDay()
            val date = Date(currentDat)
            var x = date.day - 1
            if (x == -1) {
                x = 6
            }
            return Date(currentDat - x * DAY)
        }

        fun getEndOfWeek(): Date {
            val currentDat = getCurrentDay()
            val date = Date(currentDat)
            var x = date.day - 1
            if (x == -1) {
                x = 6
            }
            return Date(currentDat - DAY * (x - 6))
        }

        @SuppressLint("SimpleDateFormat")
        fun dateToString(date: Date): String {
            val format = SimpleDateFormat("dd.MM.yyy")
            return format.format(date)
        }
    }
}