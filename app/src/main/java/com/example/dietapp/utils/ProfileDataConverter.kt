package com.example.dietapp.utils

import android.content.Context
import com.example.dietapp.R
import com.example.dietapp.utils.ArrayUtil.Companion.getArrayList

class ProfileDataConverter {
    companion object {
        private const val MAN = R.string.man
        private const val WOMAN = R.string.woman
        private const val ACTIVITIES = R.array.activities
        private const val GOALS = R.array.goals

        private fun activities(context: Context) = getArrayList(ACTIVITIES, context)
        private fun goals(context: Context) = getArrayList(GOALS, context)

        fun genderBoolToString(bool: Boolean?, context: Context): String {
            if (bool == null) return "Wybierz"
            return if (bool) context.getString(MAN) else context.getString(WOMAN)
        }

        fun genderStringToBool(string: String, context: Context): Boolean {
            return string == context.getString(MAN)
        }

        fun activityIntToString(int: Int?, context: Context): String {
            if (int == null || int == -1) return "Wybierz"
            return activities(context)[int]
        }

        fun activityStringToInt(string: String, context: Context): Int {
            return activities(context).indexOf(string)
        }

        fun goalIntToString(int: Int?, context: Context): String {
            if (int == null || int == -1) return "Wybierz"
            return goals(context)[int]
        }

        fun goalStringToInt(string: String, context: Context): Int {
            return goals(context).indexOf(string)
        }
    }
}