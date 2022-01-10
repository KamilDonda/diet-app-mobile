package com.example.dietapp.utils

import android.content.Context

class ArrayUtil {
    companion object {
        private fun Array<String>.getAsArrayList(): ArrayList<String> {
            val list = ArrayList<String>()
            this.forEach { list.add(it) }
            return list
        }

        fun getArrayList(id: Int, context: Context): ArrayList<String> {
            return context.resources.getStringArray(id).getAsArrayList()
        }
    }
}